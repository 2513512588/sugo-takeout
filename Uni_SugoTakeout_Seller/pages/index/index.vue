<template>
	<view class="wrap seller-wrap">
		<u-navbar bgColor="transparent" leftIcon="" rightIcon="setting-fill" class="u-flex" >
			<u-text slot="center" text="店铺运营中心" bold size="17"></u-text>
		</u-navbar>
		<view class="u-flex u-flex-row-start">
			<u-avatar :src="seller.logo" shape="square" ></u-avatar>
			<u-text :text="seller.sellerName" bold margin="0 5px"></u-text>
		</view>
		<u-gap height="15px"></u-gap>
		<view class="card">
			<u-grid :border="false" :col="4">
				<u-grid-item v-for="(item, index) in storeData" :key="index">
					<u-text :text="item.value" color="#000" size="18px" bold ></u-text>
					<u-text :text="item.name" color="#999" size="13px"></u-text>
					<u-gap height="15px" v-if="index <= 3"></u-gap>
				</u-grid-item>
			</u-grid>
			<u-gap height="5px"></u-gap>
		</view>
		<u-gap height="10px"></u-gap>
		<view class="card">
			<u-gap height="4px"></u-gap>
			<view class="wrap u-flex">
				<u-text class="section-title" text="常用工具" bold size="15px"></u-text>
			</view>
			<u-gap height="8px"></u-gap>
			<u-grid :border="false" :col="4">
				<u-grid-item v-for="(item, index) in menuList" :key="index" @click="clickMenuItem(item)">
					<u-icon :name="item.icon" size="36px" :color="item.color"></u-icon>
					<u-text :text="item.name" color="#333" size="13px"></u-text>
				</u-grid-item>
			</u-grid>
			<u-gap height="4px"></u-gap>
		</view>
		
		<u-gap height="10px"></u-gap>
		<view class="card">
			<u-gap height="4px"></u-gap>
			<view class="wrap u-flex">
				<u-text class="section-title" text="昨日店铺数据" bold size="15px"></u-text>
			</view>
			<u-gap height="8px"></u-gap>
			<u-grid :border="false" :col="3">
				<u-grid-item v-for="(item, index) in yesterdayStoreData" :key="index">
					<u-text :text="item.value" color="#000" size="18px" bold ></u-text>
					<u-text :text="item.name" color="#999" size="13px"></u-text>
				</u-grid-item>
			</u-grid>
			<u-gap height="4px"></u-gap>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				seller: {},
				storeData: [
					{
						name: '待支付',
						value: 5
					},
					{
						name: '待出餐',
						value: 32
					},
					{
						name: '售后/退款',
						value: 3
					},
					{
						name: '综合评分',
						value: 4.6
					},
					{
						name: '支付金额(元)',
						value: '4999.00'
					},
					{
						name: '支付人数',
						value: 320
					},
					{
						name: '支付订单数',
						value: 325
					},
					{
						name: '支付客单价(元)',
						value: 15.60
					}
				],
				yesterdayStoreData: [
					{
						name: '预计收入/元',
						value: 1540.17
					},
					{
						name: '有效订单/单',
						value: 54
					},
					{
						name: '访客数',
						value: 139
					}
				],
				menuList: [
					{
						name: '商品管理',
						icon: 'bag-fill',
						color: '#f57622'
					},
					{
						name: '订单管理',
						icon: 'file-text-fill',
						color: '#f7ba2e',
						url: '/pages/order/order'
					},
					{
						name: '数据中心',
						icon: 'share-fill',
						color: '#3784f2'
					},
					{
						name: '敬请期待',
						icon: 'grid-fill',
						color: '#4c67f7'
					}
				]
			}
		},
		methods: {
			clickMenuItem(item){
				if(item.url){
					uni.navigateTo({
						url: item.url
					})
				}
			},
			async loadData(){
				let res = await this.$q({
					url: '/seller/takeout/total-data',
					needToken: true
				})
				this.seller = res.data
				this.storeData[0].value = this.seller.quantityToBePaid
				this.storeData[1].value = this.seller.numberOfMealsToBeServed
				this.storeData[2].value = this.seller.refundQuantity
				this.storeData[3].value = this.seller.score
				this.storeData[4].value = this.seller.amount
				this.storeData[5].value = this.seller.numberOfPayers
				this.storeData[6].value = this.seller.numberOfPaymentOrders
				this.storeData[7].value = this.seller.customerUnitPrice
				
				this.yesterdayStoreData[0].value = this.seller.estimatedIncome
				this.yesterdayStoreData[1].value = this.seller.effectiveOrderQuantity
				this.yesterdayStoreData[2].value = this.seller.numberOfVisitors
			}
		},
		onLoad() {
			// uni.removeStorageSync('token')
			if (!uni.getStorageSync('token')) {
				uni.navigateTo({
					url: '/pages/login/login'
				})
			}
			this.loadData()
		},
		onShow() {
			
		},
		async onPullDownRefresh() {
			await this.loadData()
			uni.stopPullDownRefresh()
		}
	}
</script>

<style lang="scss">
	.seller-wrap{
		background-image: linear-gradient(to bottom, #f9c630, #fff 60%);
		min-height: 100vh;
	}
	
	.card{
		width: 100%;
	}
	

</style>
