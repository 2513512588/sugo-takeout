<template>
	<view class="settings-wrap">
		<u-gap height="10px"></u-gap>
		<u-cell-group >
			<u-cell size="larger" title="个人信息" label="头像、昵称、收货地址" isLink url="/pages/user/user-info"></u-cell>
			<u-cell size="larger" title="账号与安全" label="修改密码、修改手机号" isLink></u-cell>
		</u-cell-group>
		<u-gap height="5px"></u-gap>
		<u-cell-group >
			<u-cell size="larger" title="支付设置" isLink></u-cell>
		</u-cell-group>
		<u-button class="logout-btn" @click="logout">退出账号</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			logout(){
				uni.clearStorageSync()
				uni.switchTab({
					url: '/pages/index/index'
				})
			}
		}
	}
</script>

<style lang="scss">
	.settings-wrap{
		background-color: $bg-gray;
		min-height: 100vh;
		overflow: hidden;
		position: relative;
	}
	.u-cell-group{
		background-color: #fff;
	}
	.logout-btn{
		background-color: $main-color !important;
		width: 95% !important;
		transform: translateX(-50%) !important;
		position: fixed !important;
		left: 50% !important;
		bottom: 5%;
		border-radius: 8px !important;
		padding: 20px 0 !important;
	}
</style>
