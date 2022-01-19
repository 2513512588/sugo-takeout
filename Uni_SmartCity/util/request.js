
const baseURL = 'http://192.168.1.40:8080'

const baseRequest = ops =>{
	return new Promise((resolve, reject) =>{
		uni.request({
			...ops,
			url: baseURL + ops.url,
			header: {
				Authorization: ops.needToken ? uni.getStorageSync('token') : undefined
			},
			dataType: 'json',
			success: (res) => {
				if(res.data.code === 200){
					if(res.data.data && res.data.data.token){
						uni.setStorageSync('token', res.data.data.token)
					}
					resolve(res.data)
				}else {
					uni.showToast({
						icon: 'none',
						title: res.data.message
					})
					if(res.data.code === 403){
						setTimeout(() =>{
							uni.navigateTo({
								url: '/pages/login/login'
							})
						}, 500)
					}
					reject()
				}
			},
			fail: (err)=> {
				uni.showToast({
					icon: 'none',
					title: '网络异常'
				})
				reject()
			}
		})
	})
}

export default{
	baseURL,
	baseRequest
}