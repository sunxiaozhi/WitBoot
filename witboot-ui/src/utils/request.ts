import axios from 'axios'

// get 请求
export function httpGet({ url, params = {} }) {
  return new Promise((resolve, reject) => {
    axios
      .get(url, {
        params
      })
      .then((res) => {
        resolve(res.data)
      })
      .catch((err) => {
        reject(err)
      })
  })
}

// post
// post请求
export function httpPost({ url, data = {}, params = {} }) {
  return new Promise((resolve, reject) => {
    axios({
      url,
      method: 'post',
      headers: {
        'Content-Type': 'application/json',
      },
      transformRequest: [
        function (data) {
          let ret = ''
          for (let it in data) {
            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
          }
          return ret
        }
      ],
      // 发送的数据
      data,
      // url参数
      params
    }).then((res) => {
      resolve(res.data)
    })
  })
}

// 请求拦截器
// axios.interceptors.request.use(
//   (config) => {
//     // 每次发送请求之前判断是否存在token
//     // 如果存在，则统一在http请求的header都加上token，这样后台根据token判断你的登录情况，此处token一般是用户完成登录后储存到localstorage里的
//     token && (config.headers.Authorization = token)
//     return config
//   },
//   (error) => {
//     return Promise.error(error)
//   }
// )

// 响应拦截器
// axios.interceptors.response.use(
//   (response) => {
//     // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据
//     // 否则的话抛出错误
//     if (response.status === 200) {
//       if (response.data.code === 511) {
//         // 未授权调取授权接口
//       } else if (response.data.code === 510) {
//         // 未登录跳转登录页
//       } else {
//         return Promise.resolve(response)
//       }
//     } else {
//       return Promise.reject(response)
//     }
//   },
//   (error) => {
//     // 我们可以在这里对异常状态作统一处理
//     if (error.response.status) {
//       // 处理请求失败的情况
//       // 对不同返回码对相应处理
//       return Promise.reject(error.response)
//     }
//   }
// )
