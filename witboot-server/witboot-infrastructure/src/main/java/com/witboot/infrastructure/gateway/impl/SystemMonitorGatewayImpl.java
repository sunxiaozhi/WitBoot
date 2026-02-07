package com.witboot.infrastructure.gateway.impl;

import com.witboot.common.utils.ReadableFormatUtil;
import com.witboot.domain.systemmonitor.gateway.SystemMonitorGateway;
import com.witboot.infrastructure.gateway.impl.common.systemmonitor.*;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统监控网关实现类，负责收集和组装系统监控数据。
 */
@Component("SystemMonitorGateway")
public class SystemMonitorGatewayImpl implements SystemMonitorGateway {

    /**
     * 获取系统监控信息。
     *
     * @return 包含监控信息的Map对象
     */
    @Override
    public Map<String, Object> monitorInfo() {
        try {
            return new HashMap<>() {{
                put("monitorInfo", collect(500));
            }};
        } catch (Exception exception) {
            return new HashMap<>();
        }
    }

    private final SystemInfo systemInfo = new SystemInfo();

    /**
     * 收集系统监控快照数据。
     *
     * @param sampleIntervalMilliseconds 采样间隔时间（毫秒）
     * @return 系统监控快照对象
     * @throws InterruptedException 如果线程被中断
     */
    public MonitorSnapshot collect(int sampleIntervalMilliseconds) throws InterruptedException {
        int intervalMilliseconds = Math.max(sampleIntervalMilliseconds, 500);
        double intervalSeconds = intervalMilliseconds / 1000.0;

        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();

        // ===== baseline（采样项）=====
        CentralProcessor processor = hardware.getProcessor();
        long[] previousSystemTicks = processor.getSystemCpuLoadTicks();
        long[][] previousProcessorTicks = processor.getProcessorCpuLoadTicks();
        long previousContextSwitches = processor.getContextSwitches();
        long previousInterrupts = processor.getInterrupts();

        List<NetworkIF> networkInterfacesBaseline = hardware.getNetworkIFs();
        List<NetworkInterfaceSnapshot> previousNetworkSnapshots = new ArrayList<>();
        for (NetworkIF networkInterface : networkInterfacesBaseline) {
            networkInterface.updateAttributes();
            previousNetworkSnapshots.add(NetworkInterfaceSnapshot.from(networkInterface));
        }

        List<HWDiskStore> diskStoresBaseline = hardware.getDiskStores();
        List<DiskStoreSnapshot> previousDiskSnapshots = new ArrayList<>();
        for (HWDiskStore diskStore : diskStoresBaseline) {
            diskStore.updateAttributes();
            previousDiskSnapshots.add(DiskStoreSnapshot.from(diskStore));
        }

        Thread.sleep(intervalMilliseconds);

        // ===== after（采样项）=====
        CpuInfo cpuInfo = buildCpuInfo(processor, previousSystemTicks, previousProcessorTicks, previousContextSwitches, previousInterrupts);
        NetworkInfo networkInfo = buildNetworkInfo(hardware.getNetworkIFs(), previousNetworkSnapshots, intervalSeconds, intervalMilliseconds);
        List<DiskInputOutputStat> diskInputOutputStatistics = buildDiskInputOutputStatistics(hardware.getDiskStores(), previousDiskSnapshots, intervalSeconds);

        // ===== 非采样项 =====
        MemoryInfo memoryInfo = buildMemoryInfo(hardware.getMemory());

        DiskInfo diskInfo = new DiskInfo();
        diskInfo.setPartitions(buildDiskPartitions(operatingSystem.getFileSystem()));
        diskInfo.setInputOutputSampleIntervalMilliseconds(intervalMilliseconds);
        diskInfo.setInputOutputStatistics(diskInputOutputStatistics);

        JvmInfo jvmInfo = buildJvmInfo();
        ServerInfo serverInfo = buildServerInfo(operatingSystem, hardware);

        MonitorSnapshot snapshot = new MonitorSnapshot();
        snapshot.setTimestamp(Instant.now());
        snapshot.setCpuInfo(cpuInfo);
        snapshot.setMemoryInfo(memoryInfo);
        snapshot.setDiskInfo(diskInfo);
        snapshot.setNetworkInfo(networkInfo);
        snapshot.setJvmInfo(jvmInfo);
        snapshot.setServerInfo(serverInfo);
        return snapshot;
    }

    /**
     * 构建CPU信息对象。
     *
     * @param processor              处理器对象
     * @param previousSystemTicks    上一次系统CPU负载ticks
     * @param previousProcessorTicks 上一次处理器CPU负载ticks
     * @param previousContextSwitches 上一次上下文切换次数
     * @param previousInterrupts     上一次中断次数
     * @return CPU信息对象
     */
    private CpuInfo buildCpuInfo(CentralProcessor processor,
                                 long[] previousSystemTicks,
                                 long[][] previousProcessorTicks,
                                 long previousContextSwitches,
                                 long previousInterrupts) {
        CpuInfo info = new CpuInfo();
        info.setName(processor.getProcessorIdentifier().getName());
        info.setVendor(processor.getProcessorIdentifier().getVendor());
        info.setPhysicalPackageCount(processor.getPhysicalPackageCount());
        info.setPhysicalProcessorCount(processor.getPhysicalProcessorCount());
        info.setLogicalProcessorCount(processor.getLogicalProcessorCount());

        info.setMaximumFrequencyHertz(processor.getMaxFreq());
        List<Long> currentFrequencies = Arrays.stream(processor.getCurrentFreq()).boxed().collect(Collectors.toList());
        info.setCurrentFrequenciesHertz(currentFrequencies);

        double totalUsage = processor.getSystemCpuLoadBetweenTicks(previousSystemTicks);
        double[] perCoreUsage = processor.getProcessorCpuLoadBetweenTicks(previousProcessorTicks);

        double totalUsagePercent = ReadableFormatUtil.roundTwoDecimals(totalUsage * 100.0);
        info.setCpuUsagePercent(totalUsagePercent);
        info.setCpuUsagePercentReadable(ReadableFormatUtil.percent(totalUsagePercent));

        info.setPerCoreUsagePercent(Arrays.stream(perCoreUsage)
                .mapToObj(v -> ReadableFormatUtil.roundTwoDecimals(v * 100.0))
                .collect(Collectors.toList()));

        info.setLoadAverage(Arrays.stream(processor.getSystemLoadAverage(3)).boxed().collect(Collectors.toList()));
        info.setContextSwitchesDelta(processor.getContextSwitches() - previousContextSwitches);
        info.setInterruptsDelta(processor.getInterrupts() - previousInterrupts);

        info.setMaximumFrequencyReadable(ReadableFormatUtil.frequencyHertz(info.getMaximumFrequencyHertz()));
        info.setCurrentFrequenciesReadable(currentFrequencies.stream()
                .map(ReadableFormatUtil::frequencyHertz)
                .collect(Collectors.toList()));

        return info;
    }

    /**
     * 构建内存信息对象。
     *
     * @param memory 全局内存对象
     * @return 内存信息对象
     */
    private MemoryInfo buildMemoryInfo(GlobalMemory memory) {
        long totalBytes = memory.getTotal();
        long availableBytes = memory.getAvailable();
        long usedBytes = totalBytes - availableBytes;

        double usedPercent = totalBytes == 0 ? 0 : ReadableFormatUtil.roundTwoDecimals(usedBytes * 100.0 / totalBytes);

        MemoryInfo info = new MemoryInfo();
        info.setTotalBytes(totalBytes);
        info.setAvailableBytes(availableBytes);
        info.setUsedBytes(usedBytes);
        info.setUsedPercent(usedPercent);

        info.setTotalReadable(ReadableFormatUtil.bytes(totalBytes));
        info.setAvailableReadable(ReadableFormatUtil.bytes(availableBytes));
        info.setUsedReadable(ReadableFormatUtil.bytes(usedBytes));
        info.setUsedPercentReadable(ReadableFormatUtil.percent(usedPercent));

        VirtualMemory virtualMemory = memory.getVirtualMemory();
        long swapTotalBytes = virtualMemory.getSwapTotal();
        long swapUsedBytes = virtualMemory.getSwapUsed();
        double swapUsedPercent = swapTotalBytes == 0 ? 0 : ReadableFormatUtil.roundTwoDecimals(swapUsedBytes * 100.0 / swapTotalBytes);

        SwapInfo swapInfo = new SwapInfo();
        swapInfo.setTotalBytes(swapTotalBytes);
        swapInfo.setUsedBytes(swapUsedBytes);
        swapInfo.setUsedPercent(swapUsedPercent);

        swapInfo.setTotalReadable(ReadableFormatUtil.bytes(swapTotalBytes));
        swapInfo.setUsedReadable(ReadableFormatUtil.bytes(swapUsedBytes));
        swapInfo.setUsedPercentReadable(ReadableFormatUtil.percent(swapUsedPercent));

        info.setSwap(swapInfo);
        return info;
    }

    /**
     * 构建磁盘分区信息列表。
     *
     * @param fileSystem 文件系统对象
     * @return 磁盘分区信息列表
     */
    private List<DiskPartitionInfo> buildDiskPartitions(FileSystem fileSystem) {
        List<DiskPartitionInfo> partitions = new ArrayList<>();
        for (OSFileStore store : fileSystem.getFileStores()) {
            long totalBytes = store.getTotalSpace();
            long freeBytes = store.getUsableSpace();
            long usedBytes = totalBytes - freeBytes;
            double usedPercent = totalBytes == 0 ? 0 : ReadableFormatUtil.roundTwoDecimals(usedBytes * 100.0 / totalBytes);

            DiskPartitionInfo partition = new DiskPartitionInfo();
            partition.setName(store.getName());
            partition.setMount(store.getMount());
            partition.setType(store.getType());
            partition.setVolume(store.getVolume());

            partition.setTotalBytes(totalBytes);
            partition.setFreeBytes(freeBytes);
            partition.setUsedBytes(usedBytes);
            partition.setUsedPercent(usedPercent);

            partition.setTotalReadable(ReadableFormatUtil.bytes(totalBytes));
            partition.setFreeReadable(ReadableFormatUtil.bytes(freeBytes));
            partition.setUsedReadable(ReadableFormatUtil.bytes(usedBytes));
            partition.setUsedPercentReadable(ReadableFormatUtil.percent(usedPercent));

            partitions.add(partition);
        }
        return partitions;
    }

    /**
     * 构建磁盘输入输出统计信息列表。
     *
     * @param diskStoresNow         当前磁盘存储列表
     * @param previousDiskSnapshots 上一次磁盘快照列表
     * @param intervalSeconds       采样间隔时间（秒）
     * @return 磁盘输入输出统计信息列表
     */
    private List<DiskInputOutputStat> buildDiskInputOutputStatistics(List<HWDiskStore> diskStoresNow,
                                                                     List<DiskStoreSnapshot> previousDiskSnapshots,
                                                                     double intervalSeconds) {

        Map<String, DiskStoreSnapshot> currentDiskSnapshotByName = new LinkedHashMap<>();
        for (HWDiskStore diskStore : diskStoresNow) {
            diskStore.updateAttributes();
            currentDiskSnapshotByName.put(diskStore.getName(), DiskStoreSnapshot.from(diskStore));
        }

        List<DiskInputOutputStat> output = new ArrayList<>();
        for (DiskStoreSnapshot previous : previousDiskSnapshots) {
            DiskStoreSnapshot current = currentDiskSnapshotByName.get(previous.name);
            if (current == null) continue;

            double readBytesPerSecond = (current.readBytes - previous.readBytes) / intervalSeconds;
            double writeBytesPerSecond = (current.writeBytes - previous.writeBytes) / intervalSeconds;

            double readOperationsPerSecond = (current.readOperations - previous.readOperations) / intervalSeconds;
            double writeOperationsPerSecond = (current.writeOperations - previous.writeOperations) / intervalSeconds;
            double transfersPerSecond = (current.transferCount - previous.transferCount) / intervalSeconds;

            DiskInputOutputStat stat = new DiskInputOutputStat();
            stat.setName(current.name);
            stat.setModel(current.model);
            stat.setSerial(current.serial);

            stat.setSizeBytes(current.sizeBytes);
            stat.setSizeReadable(ReadableFormatUtil.bytes(current.sizeBytes));

            stat.setReadBytesPerSecond(ReadableFormatUtil.roundTwoDecimals(readBytesPerSecond));
            stat.setWriteBytesPerSecond(ReadableFormatUtil.roundTwoDecimals(writeBytesPerSecond));
            stat.setReadReadablePerSecond(ReadableFormatUtil.bytesPerSecond(readBytesPerSecond));
            stat.setWriteReadablePerSecond(ReadableFormatUtil.bytesPerSecond(writeBytesPerSecond));

            stat.setReadOperationsPerSecond(ReadableFormatUtil.roundTwoDecimals(readOperationsPerSecond));
            stat.setWriteOperationsPerSecond(ReadableFormatUtil.roundTwoDecimals(writeOperationsPerSecond));
            stat.setTransfersPerSecond(ReadableFormatUtil.roundTwoDecimals(transfersPerSecond));

            stat.setCurrentQueueLength(current.currentQueueLength);
            stat.setTransferTimeMillisecondsTotal(current.transferTimeMillisecondsTotal);

            output.add(stat);
        }

        return output;
    }

    /**
     * 构建网络信息对象。
     *
     * @param networkInterfacesNow      当前网络接口列表
     * @param previousNetworkSnapshots  上一次网络快照列表
     * @param intervalSeconds           采样间隔时间（秒）
     * @param intervalMilliseconds      采样间隔时间（毫秒）
     * @return 网络信息对象
     */
    private NetworkInfo buildNetworkInfo(List<NetworkIF> networkInterfacesNow,
                                         List<NetworkInterfaceSnapshot> previousNetworkSnapshots,
                                         double intervalSeconds,
                                         int intervalMilliseconds) {

        Map<String, NetworkInterfaceSnapshot> currentSnapshotByName = new LinkedHashMap<>();
        for (NetworkIF networkInterface : networkInterfacesNow) {
            networkInterface.updateAttributes();
            currentSnapshotByName.put(networkInterface.getName(), NetworkInterfaceSnapshot.from(networkInterface));
        }

        List<NetworkInterfaceStat> interfaceStats = new ArrayList<>();
        for (NetworkInterfaceSnapshot previous : previousNetworkSnapshots) {
            NetworkInterfaceSnapshot current = currentSnapshotByName.get(previous.name);
            if (current == null) continue;

            double receiveBytesPerSecond = (current.totalReceivedBytes - previous.totalReceivedBytes) / intervalSeconds;
            double transmitBytesPerSecond = (current.totalTransmittedBytes - previous.totalTransmittedBytes) / intervalSeconds;

            double receivePacketsPerSecond = (current.totalReceivedPackets - previous.totalReceivedPackets) / intervalSeconds;
            double transmitPacketsPerSecond = (current.totalTransmittedPackets - previous.totalTransmittedPackets) / intervalSeconds;

            NetworkInterfaceStat stat = new NetworkInterfaceStat();
            stat.setName(current.name);
            stat.setDisplayName(current.displayName);
            stat.setMacAddress(current.macAddress);
            stat.setIpv4Addresses(current.ipv4Addresses);
            stat.setIpv6Addresses(current.ipv6Addresses);

            stat.setSpeedBitsPerSecond(current.speedBitsPerSecond);
            stat.setSpeedReadable(ReadableFormatUtil.networkBitRateBitsPerSecond(current.speedBitsPerSecond));

            stat.setReceiveBytesPerSecond(ReadableFormatUtil.roundTwoDecimals(receiveBytesPerSecond));
            stat.setTransmitBytesPerSecond(ReadableFormatUtil.roundTwoDecimals(transmitBytesPerSecond));
            stat.setReceiveReadablePerSecond(ReadableFormatUtil.bytesPerSecond(receiveBytesPerSecond));
            stat.setTransmitReadablePerSecond(ReadableFormatUtil.bytesPerSecond(transmitBytesPerSecond));

            stat.setReceivePacketsPerSecond(ReadableFormatUtil.roundTwoDecimals(receivePacketsPerSecond));
            stat.setTransmitPacketsPerSecond(ReadableFormatUtil.roundTwoDecimals(transmitPacketsPerSecond));

            interfaceStats.add(stat);
        }

        NetworkInfo info = new NetworkInfo();
        info.setSampleIntervalMilliseconds(intervalMilliseconds);
        info.setInterfaces(interfaceStats);
        return info;
    }

    /**
     * 构建JVM信息对象。
     *
     * @return JVM信息对象
     */
    private JvmInfo buildJvmInfo() {
        Runtime runtime = Runtime.getRuntime();

        JvmInfo info = new JvmInfo();
        info.setJavaVersion(System.getProperty("java.version"));
        info.setJavaVendor(System.getProperty("java.vendor"));
        info.setJavaHome(System.getProperty("java.home"));
        info.setJavaVirtualMachineName(System.getProperty("java.vm.name"));
        info.setJavaVirtualMachineVersion(System.getProperty("java.vm.version"));

        info.setProcessIdAtHost(ManagementFactory.getRuntimeMXBean().getName());
        info.setUptimeMilliseconds(ManagementFactory.getRuntimeMXBean().getUptime());
        info.setStartTime(Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().getStartTime()));

        long heapTotalBytes = runtime.totalMemory();
        long heapFreeBytes = runtime.freeMemory();
        long heapMaxBytes = runtime.maxMemory();
        long heapUsedBytes = heapTotalBytes - heapFreeBytes;

        double heapUsedPercentOfMax = heapMaxBytes <= 0 ? 0 : ReadableFormatUtil.roundTwoDecimals(heapUsedBytes * 100.0 / heapMaxBytes);

        info.setHeapTotalBytes(heapTotalBytes);
        info.setHeapUsedBytes(heapUsedBytes);
        info.setHeapMaxBytes(heapMaxBytes);
        info.setHeapUsedPercentOfMax(heapUsedPercentOfMax);

        info.setHeapTotalReadable(ReadableFormatUtil.bytes(heapTotalBytes));
        info.setHeapUsedReadable(ReadableFormatUtil.bytes(heapUsedBytes));
        info.setHeapMaxReadable(ReadableFormatUtil.bytes(heapMaxBytes));
        info.setHeapUsedPercentOfMaxReadable(ReadableFormatUtil.percent(heapUsedPercentOfMax));

        info.setThreadCount(ManagementFactory.getThreadMXBean().getThreadCount());
        info.setDaemonThreadCount(ManagementFactory.getThreadMXBean().getDaemonThreadCount());
        info.setLoadedClassCount(ManagementFactory.getClassLoadingMXBean().getLoadedClassCount());

        return info;
    }

    /**
     * 构建服务器信息对象。
     *
     * @param operatingSystem 操作系统对象
     * @param hardware        硬件抽象层对象
     * @return 服务器信息对象
     */
    private ServerInfo buildServerInfo(OperatingSystem operatingSystem, HardwareAbstractionLayer hardware) {
        ServerInfo info = new ServerInfo();

        info.setOperatingSystemFamily(operatingSystem.getFamily());
        info.setOperatingSystemVersion(operatingSystem.getVersionInfo().getVersion());
        info.setOperatingSystemBuildNumber(operatingSystem.getVersionInfo().getBuildNumber());
        info.setArchitecture(System.getProperty("os.arch"));

        ComputerSystem computerSystem = hardware.getComputerSystem();
        info.setSystemManufacturer(computerSystem.getManufacturer());
        info.setSystemModel(computerSystem.getModel());
        info.setSystemSerialNumber(computerSystem.getSerialNumber());
        info.setFirmwareName(computerSystem.getFirmware() == null ? null : computerSystem.getFirmware().getName());

        info.setBootTime(Instant.ofEpochSecond(operatingSystem.getSystemBootTime()));

        try {
            InetAddress localHost = InetAddress.getLocalHost();
            info.setHostName(localHost.getHostName());
            info.setHostAddress(localHost.getHostAddress());
        } catch (Exception e) {
            info.setHostName(null);
            info.setHostAddress(null);
        }

        List<NetworkInterfaceBasic> basics = new ArrayList<>();
        for (NetworkIF networkInterface : hardware.getNetworkIFs()) {
            networkInterface.updateAttributes();

            NetworkInterfaceBasic basic = new NetworkInterfaceBasic();
            basic.setName(networkInterface.getName());
            basic.setDisplayName(networkInterface.getDisplayName());
            basic.setMacAddress(networkInterface.getMacaddr());
            basic.setIpv4Addresses(Arrays.asList(networkInterface.getIPv4addr()));
            basic.setIpv6Addresses(Arrays.asList(networkInterface.getIPv6addr()));
            basic.setMaximumTransmissionUnit(networkInterface.getMTU());

            long speedBitsPerSecond = networkInterface.getSpeed();
            basic.setSpeedBitsPerSecond(speedBitsPerSecond);
            basic.setSpeedReadable(ReadableFormatUtil.networkBitRateBitsPerSecond(speedBitsPerSecond));

            basic.setTotalReceivedBytes(networkInterface.getBytesRecv());
            basic.setTotalTransmittedBytes(networkInterface.getBytesSent());
            basic.setTotalReceivedReadable(ReadableFormatUtil.bytes(networkInterface.getBytesRecv()));
            basic.setTotalTransmittedReadable(ReadableFormatUtil.bytes(networkInterface.getBytesSent()));

            basics.add(basic);
        }
        info.setNetworkInterfaces(basics);

        return info;
    }

    // ===== Snapshots =====

    /**
     * 磁盘存储快照类，用于记录磁盘状态。
     */
    private static class DiskStoreSnapshot {
        String name;
        String model;
        String serial;

        long sizeBytes;
        long readBytes;
        long writeBytes;

        long readOperations;
        long writeOperations;
        long transferCount;

        long currentQueueLength;
        long transferTimeMillisecondsTotal;

        static DiskStoreSnapshot from(HWDiskStore diskStore) {
            DiskStoreSnapshot snapshot = new DiskStoreSnapshot();
            snapshot.name = diskStore.getName();
            snapshot.model = diskStore.getModel();
            snapshot.serial = diskStore.getSerial();
            snapshot.sizeBytes = diskStore.getSize();
            snapshot.readBytes = diskStore.getReadBytes();
            snapshot.writeBytes = diskStore.getWriteBytes();
            snapshot.readOperations = diskStore.getReads();
            snapshot.writeOperations = diskStore.getWrites();
            //snapshot.transferCount = diskStore.getTransferCount();
            snapshot.currentQueueLength = diskStore.getCurrentQueueLength();
            snapshot.transferTimeMillisecondsTotal = diskStore.getTransferTime();
            return snapshot;
        }
    }

    /**
     * 网络接口快照类，用于记录网络接口状态。
     */
    private static class NetworkInterfaceSnapshot {
        String name;
        String displayName;
        String macAddress;
        List<String> ipv4Addresses;
        List<String> ipv6Addresses;

        long speedBitsPerSecond;

        long totalReceivedBytes;
        long totalTransmittedBytes;
        long totalReceivedPackets;
        long totalTransmittedPackets;

        static NetworkInterfaceSnapshot from(NetworkIF networkInterface) {
            NetworkInterfaceSnapshot snapshot = new NetworkInterfaceSnapshot();
            snapshot.name = networkInterface.getName();
            snapshot.displayName = networkInterface.getDisplayName();
            snapshot.macAddress = networkInterface.getMacaddr();
            snapshot.ipv4Addresses = Arrays.asList(networkInterface.getIPv4addr());
            snapshot.ipv6Addresses = Arrays.asList(networkInterface.getIPv6addr());
            snapshot.speedBitsPerSecond = networkInterface.getSpeed();

            snapshot.totalReceivedBytes = networkInterface.getBytesRecv();
            snapshot.totalTransmittedBytes = networkInterface.getBytesSent();
            snapshot.totalReceivedPackets = networkInterface.getPacketsRecv();
            snapshot.totalTransmittedPackets = networkInterface.getPacketsSent();
            return snapshot;
        }
    }
}
