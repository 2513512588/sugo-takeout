<template>
	<view class="login_container">
		<u-navbar left-text="返回" safeAreaInsetTop bgColor="rgba(0, 0, 0, 0.1)" fixed autoBack>
			<template #left>
				<u-icon name="arrow-left" size="20" label="返回" color="#fff" labelColor="#fff"></u-icon>
			</template>
		</u-navbar>
		<view class="login_wrap">
			<h2 class="login_title">用户登录</h2>
			<u--form :model="form" :rules="rules" ref="uForm" errorType="toast">
				<u-form-item prop="username" borderBottom>
					<u--input v-model="form.username" placeholderStyle="color: #fff" color="#fff" border="none" placeholder="请输入用户名">
					</u--input>
				</u-form-item>
				<u-form-item prop="password" borderBottom>
					<u--input type="password" placeholderStyle="color: #fff" color="#fff"  v-model="form.password" placeholder="请输入密码"
						border="none"></u--input>
				</u-form-item>
				<u-form-item>
					<u-button @click="login" type="primary" style="width: 100%;" text="登录"></u-button>
				</u-form-item>
			</u--form>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					username: '',
					password: ''
				},
				rules: {
					username: [{
						type: 'string',
						required: true,
						message: '请输入用户名',
						trigger: ['blur', 'change']
					}],
					password: [{
						type: 'string',
						required: true,
						message: '请输入密码',
						trigger: ['blur', 'change']
					}],
				},
			}
		},
		onLoad() {
			console.log('加载登录页面');
		},
		onBackPress(e) {
			uni.switchTab({
				url: '/pages/index/index'
			});
			// plus.navigator.setFullscreen(false);
			return true;
		},
		onShow() {
			// #ifdef APP-PLUS
			// 设置应用全屏显示！
			// plus.navigator.setFullscreen(true);
			// #endif
		},
		onHide() {
			// #ifdef APP-PLUS
			// 设置应用非全屏显示！
			// plus.navigator.setFullscreen(false);
			// #endif
		},
		methods: {
			login() {
				this.$refs.uForm.validate().then(res => {
					console.log('验证通过');
					this.$q({
						url: '/api/commons/user/login',
						data: this.form,
						method: 'POST',
					}).then(res => {
						// uni.switchTab({
						// 	url: '/pages/profile/profile'
						// })
						uni.navigateBack()
					})
				}).catch(error => {
					uni.showToast({
						title: '请输入正确的内容',
						icon: 'none',
						duration: 2000
					})
				})
			},
		}
	}
</script>

<style>
	.login_container {
		width: 100%;
		height: 100vh;
		overflow: hidden;
		background: url(/static/login-bg.png) no-repeat 0/cover;
		box-sizing: border-box;
		position: fixed;
	}

	.login_wrap {
		padding: 0 15px;
		width: 90%;
		margin-top: 120px;
	}

	.login_title {
		color: #fff;
		font-size: 30px;
		margin-bottom: 30px;
	}
</style>
