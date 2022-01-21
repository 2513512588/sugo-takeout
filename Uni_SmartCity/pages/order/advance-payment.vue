<template>
	<view class="order-wrap">
		<view class="card">
			<view @click="showChooseAddress = true" v-if="addressList.length > 0">
				<AddressItemShow :data="addressList[addressActive]"></AddressItemShow>
			</view>
			<u-cell v-else :border="false" isLink url="/pages/address/address">
				<u-gap height="3"></u-gap>
				<u-icon slot="title" label="添加收货地址" name="plus" size="18" space="7"></u-icon>
				<u-gap height="3"></u-gap>
			</u-cell>
			<u-cell title="立即送出" isLink :border="false" :iconStyle="iconStyle" >
				<u-text slot="value" type="warning" :text="'大约{0}分钟送达'.format(seller.deliveryTime)" style="justify-content: flex-end;"></u-text>
			</u-cell>
		</view>
		<view class="card md">
			<u-gap height="6"></u-gap>
			<u-text :text="seller.name"></u-text>
			<u-divider></u-divider>
			<FoodItem v-if="settlementGoodsList.length > 0" v-for="(item,index) in settlementGoodsList"
				:key="item.id + '-' + index" :item="item" :disabledBtn="true"></FoodItem>
			<u-gap height="60"></u-gap>
		</view>

		<u-action-sheet title="选择收货地址" round="8px" :show="showChooseAddress" @close="showChooseAddress = false" >
			<view style="padding-bottom: 80px;">
				<u-radio-group placement="column" v-model="addressActive" v-if="update" @change="onChangeChooseAddr">
					<view v-for="(item,index) in addressList" :key="item.id">
						<view class="address-wrap" @click="addressActive = index; onChangeChooseAddr()">
							<u-radio :name="index"></u-radio>
							<AddressItemShow style="width: 95%;" :data="item">
								<u-icon name="edit-pen" size="22" @click="goEditAddress(item.id)"></u-icon>
							</AddressItemShow>
						</view>
						<u-divider></u-divider>
					</view>
				</u-radio-group>
			</view>
			<u-button type="warning" @click="goEditAddress('')">添加收货地址</u-button>
		</u-action-sheet>

		<view class="statistics-bar">
			<u-icon name="rmb" :label="totalPrice" color="#fff" labelColor="#fff" :size="20" :labelSize="25"></u-icon>
			<u-button class="buy-btn" type="warning" @click="order">提交订单</u-button>
		</view>
	</view>
</template>

<script>
	import AddressItemShow from '@/components/AddressItemShow.vue'
	import FoodItem from '@/components/FoodItem.vue'

	export default {
		data() {
			return {
				iconStyle: {
					'font-size': '11px !important'
				},
				seller: {},
				settlementGoodsList: [],
				totalPrice: 0,
				addressList: [],
				addressActive: 0,
				showChooseAddress: false,
				options: [{
					text: '删除'
				}],
				update: true,
				sellerId: ''
			}
		},
		components: {
			AddressItemShow,
			FoodItem
		},
		methods: {
			order() {
				if (this.addressList.length > 0) {
					this.$q({
						url: '/prod-api/api/takeout/order/create',
						token: true,
						method: 'POST',
						type: 'json',
						data: {
							addressDetail: this.addressList[this.addressActive].addressDetail,
							name: this.addressList[this.addressActive].name,
							phone: this.addressList[this.addressActive].phone,
							label: this.addressList[this.addressActive].label,
							amount: this.totalPrice,
							orderItemList: this.settlementGoodsList.map(item => {
								return {
									productId: item.id,
									quantity: item.quantity
								}
							}),
							sellerId: this.seller.id
						}
					}).then(res => {

						let scartGoodsList = uni.getStorageSync('scartGoodsList') || []
						this.settlementGoodsList.forEach(item => {
							let index = scartGoodsList.findIndex(el => el.id === item.id)
							if (index !== -1) {
								scartGoodsList.splice(index, 1)
							}
						})
						uni.setStorageSync('scartGoodsList', scartGoodsList)
						uni.removeStorageSync('settlementGoodsList')

						uni.navigateTo({
							url: '/pages/takeout/pay?type=1&orderNo=' + res.orderNo
						})
					})
				} else {
					uni.showToast({
						icon: 'none',
						title: '请先添加收货地址',
						duration: 2000
					})
				}
			},
			goEditAddress(id) {
				uni.navigateTo({
					url: '/pages/takeout/address?id=' + id
				})
			},
			deleteAddress(id) {
				console.log(id);
			},
			onChangeChooseAddr() {
				this.showChooseAddress = false
			},
			deliveryTime(addrId){
				this.$q({
					url: '/api/takeout/address/deliveryTime',
					data: {
						addrId: addrId,
						sellerId: this.sellerId
					},
					needToken: true
				}).then(res =>{
					this.seller.deliveryTime = res.data
					this.$forceUpdate()
				})
			}
		},
		onShow() {

		},
		onLoad(options) {
			this.sellerId = options.id
			this.$q({
				url: '/api/takeout/seller/baseInfo/' + this.sellerId
			}).then(res =>{
				
			})
			uni.getLocation({
				type: 'wgs84',
				geocode: true,
				success: (res) => {
					this.$q({
						url: '/api/takeout/address/proposal',
						needToken: true,
						data: {
							myLocation: encodeURI([res.latitude, res.longitude].join(','))
						}
					}).then(res => {
						this.addressList.push(res.data)
						this.deliveryTime(res.data.id)
					})
				},
				fail: (e) => {
					uni.showToast({
						title: '获取的定位信息失败',
						icon: 'none'
					})
				}
			})
		}
	}
</script>

<style>
	.card {
		background-color: #fff;
		margin: 10px auto;
		width: 95%;
		border-radius: 8px;
		box-shadow: 0 0 8px #eee;
		border: 1px solid #eee;
		overflow: hidden;
	}

	.statistics-bar {
		position: fixed;
		left: 50%;
		transform: translateX(-50%);
		bottom: 10px;
		width: 90%;
		height: 50px;
		border-radius: 25px;
		background-color: #333;
		display: flex;
		align-items: center;
		justify-content: space-between;
		overflow: hidden;
		padding-left: 20px;
		z-index: 99;
	}

	.buy-btn {
		width: 120px !important;
		height: 50px !important;
		margin: 0;
	}

	.address-wrap {
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 100%;
		box-sizing: border-box;
		padding-left: 20px;
	}

	.u-divider {
		margin: 7px 0 !important;
	}
</style>
