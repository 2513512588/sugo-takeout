<template>
	<view class="user-wrap">
		<u-gap height="10px"></u-gap>
		<view class="wrap settings-wrap" >
			<navigator url="/pages/user/settings">
				<u-text text="设置" ></u-text>
			</navigator>
		</view>
		<u-gap height="15px"></u-gap>
		<view class="u-flex u-flex-row-start wrap">
			<u-avatar :src="user.avatar" size="60" :text="user.username" ></u-avatar>
			<u-text :text="user.nickname || '未设置用户昵称'" bold size="18px" margin="0 10px"></u-text>
		</view>
		<u-gap height="20px"></u-gap>
		<view class="card">
			<u-grid :border="false" :col="4">
				<u-grid-item v-for="(item, index) in baseMenuList" :key="index">
					<u-icon :name="item.icon" size="36px"></u-icon>
					<u-text :text="item.name" color="#333" size="13px"></u-text>
				</u-grid-item>
			</u-grid>
		</view>
		<u-gap height="10px"></u-gap>
		<view class="card">
			<u-gap height="4px"></u-gap>
			<view class="wrap u-flex">
				<u-text class="section-title" text="我的订单" bold size="15px"></u-text>
				<navigator url="/pages/order/order">
					<u-icon name="arrow-right" label="全部订单" labelPos="left" color="#999" labelColor="#999" size="13px" labelSize="13px"></u-icon>
				</navigator>
			</view>
			<u-gap height="8px"></u-gap>
			<u-grid :border="false" :col="4">
				<u-grid-item v-for="(item, index) in orderMenuList" :key="index">
					<u-icon :name="item.icon" size="36px"></u-icon>
					<u-text :text="item.name" color="#333" size="13px"></u-text>
				</u-grid-item>
			</u-grid>
			<u-gap height="4px"></u-gap>
		</view>
		
		<u-gap height="10px"></u-gap>
		<view class="card">
			<view class="wrap u-flex">
				<u-text class="section-title" text="我的钱包" bold size="15px"></u-text>
				<u-icon name="arrow-right" label="进入钱包" labelPos="left" color="#999" labelColor="#999" size="13px" labelSize="13px"></u-icon>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {},
				baseMenuList: [
					{
						name: '购物车',
						icon: 'shopping-cart'
					},
					{
						name: '收藏',
						icon: 'star'
					},
					{
						name: '足迹',
						icon: 'hourglass'
					},
					{
						name: '红包卡券',
						icon: 'coupon'
					}
				],
				orderMenuList: [
					{
						name: '待付款',
						icon: 'bag'
					},
					{
						name: '待收货',
						icon: 'car'
					},
					{
						name: '待评价',
						icon: 'chat'
					},
					{
						name: '退款/售后',
						icon: 'file-text'
					}
				]
			}
		},
		methods: {
			
		},
		onShow() {
			this.$q({
				url: '/api/commons/user/getInfo',
				needToken: true
			}).then(res =>{
				this.user = res.data
			})
		}
	}
</script>

<style lang="scss">
	.user-wrap{
		padding-top: var(--status-bar-height);
		background-image: linear-gradient(to bottom, #fdfadb, #fff 40%);
	}
	.settings-wrap{
		width: 100%;
		display: flex;
		justify-content: flex-end;
		align-items: center;
		
		.u-text{
			flex: unset;
		}
	}
</style>
