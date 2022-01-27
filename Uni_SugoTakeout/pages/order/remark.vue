<template>
	<view class="wrap">
		<u-gap height="10px"></u-gap>
		<u--textarea v-model="remark" focus placeholder="请输入口味、偏好等要求，不支持额外代购其他物品哦～"  count maxlength="50" height="120"></u--textarea>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				remark: ''
			};
		},
		onNavigationBarButtonTap() {
			uni.$emit("setRemark", this.remark);
			uni.navigateBack()
		}
	}
</script>

<style lang="scss">
	.u-textarea{
		background-color: #f5f7fa !important;
		
		::v-deep .u-textarea__count{
			background-color: transparent !important;
		}
	}
</style>
