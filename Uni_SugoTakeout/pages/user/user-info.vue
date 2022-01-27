<template>
	<view class="settings-wrap">
		<u-gap height="10px"></u-gap>
		<u-cell-group>
			<u-cell size="larger" title="头像" isLink @click="changeAvatar">
				<u-avatar size="45" slot="value" :src="user.avatar" :text="user.username"></u-avatar>
			</u-cell>
			<u-cell size="larger" title="昵称" :value="user.nickname" isLink></u-cell>
			<u-cell size="larger" title="收货地址" value="修改/添加" isLink></u-cell>
		</u-cell-group>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {}
			}
		},
		methods: {
			changeAvatar(){
				uni.chooseImage({
					success: () => {
						
					}
				})
			}
		},
		onLoad() {
			this.$q({
				url: '/api/commons/user/getInfo',
				needToken: true
			}).then(res => {
				this.user = res.data
			})
		}
	}
</script>

<style lang="scss">
	.settings-wrap {
		background-color: $bg-gray;
		min-height: 100vh;
		overflow: hidden;
		position: relative;
	}

	.u-cell-group {
		background-color: #fff;
	}
</style>
