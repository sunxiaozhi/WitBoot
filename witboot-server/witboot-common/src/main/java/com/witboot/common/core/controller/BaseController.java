package com.witboot.common.core.controller;

import com.github.pagehelper.PageHelper;
import com.witboot.common.utils.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * BaseController
 *
 * @author sunxiaozhi
 */
public class BaseController {
    public void startPage() {
        HttpServletRequest httpServletRequest = JakartaServletUtil.getRequest();

        String pageNumParam = httpServletRequest.getParameter("pageNum");
        String pageSizeParam = httpServletRequest.getParameter("pageSize");

        int pageNum = Integer.parseInt(StringUtils.isEmpty(pageNumParam) ? "1" : pageNumParam);
        int pageSize = Integer.parseInt(StringUtils.isEmpty(pageSizeParam) ? "10" : pageSizeParam);

        PageHelper.startPage(pageNum, pageSize);
    }
}
