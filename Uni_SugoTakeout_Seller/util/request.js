
const baseURL = 'http://192.168.1.40:8081'

const baseRequest = ops =>{
	uni.showLoading({
		mask: true,
		title: 'loading...'
	})
	return new Promise((resolve, reject) =>{
		uni.request({
			...ops,
			url: baseURL + ops.url,
			timeout: 8000,
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
			},
			complete: () => {
				uni.hideLoading()
			}
		})
	})
}

export default{
	baseURL,
	baseRequest
}