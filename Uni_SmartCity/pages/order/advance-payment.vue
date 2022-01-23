<template>
	<view class="order-wrap">

		<!-- 收货地址等信息 -->
		<view class="card">
			<view @click="showChooseAddress = true" v-if="addressList.length > 0">
				<AddressItemShow :data="addressList[addressActive]"></AddressItemShow>
			</view>
			<u-cell v-else :border="false" isLink url="/pages/address/address">
				<u-gap height="3"></u-gap>
				<u-icon slot="title" label="添加收货地址" name="plus" size="18" space="7"></u-icon>
				<u-gap height="3"></u-gap>
			</u-cell>
			<u-cell title="立即送出" isLink :border="false" :iconStyle="iconStyle">
				<u-text slot="value" type="warning" :text="'大约{0}分钟送达'.format(seller.deliveryTime)"
					 class="u-flex-row-end"></u-text>
			</u-cell>
		</view>

		<!-- 加购的产品信息 -->
		<view class="card wrap goods-card">
			<u-text :text="seller.name"></u-text>
			<u-divider></u-divider>

			<u-gap height="5px"></u-gap>
			<view class="goods-item u-flex u-flex-col-start" v-for="(item,index) in settlementGoodsList">
				<view class="goods-content-wrap u-flex u-flex-col-start u-flex-row-start">
					<image :src="item.goods.cover" mode="aspectFill" class="goods-cover"></image>
					<view class="goods-desc-wrap">
						<u-text :text="item.goods.name" lines="1" bold size="14px"></u-text>
						<u-gap height="3px"></u-gap>
						<u-text :text="item.goods.description" lines="1" color="#999" size="12px"></u-text>
						<u-gap height="3px"></u-gap>
						<u-text :text="'x' + item.quantity" lines="1" color="#999" size="12px"></u-text>
					</view>
				</view>
				<cn-money :money="item.goods.price" color="#000" size="18px"></cn-money>
			</view>
			<u-gap height="5px"></u-gap>
			<view class="u-flex">
				<u-text text="打包费" size="14px" color="#999"></u-text>
				<cn-money :money="seller.packageFee" color="#000"></cn-money>
			</view>
			<u-gap height="6px"></u-gap>
			<view class="u-flex">
				<u-text text="配送费" size="14px" color="#999"></u-text>
				<cn-money :money="seller.deliveryFee" color="#000"></cn-money>
			</view>
			<u-gap height="6px"></u-gap>
			<view class="u-flex">
				<u-text text="店铺满减" size="14px" color="#999"></u-text>
				<cn-money :money="seller.discount || 0" negative iconNormalSize></cn-money>
			</view>
			<u-gap height="6px"></u-gap>
			<view class="u-flex">
				<u-text text="商家代金劵" size="14px" color="#999"></u-text>

				<u-text v-show="!seller.coupon" text="暂无可用" color="#999" size="13px"></u-text>
				<cn-money v-show="seller.coupon" :money="seller.coupon" negative iconNormalSize></cn-money>
			</view>
			<u-gap height="6px"></u-gap>
		</view>

		<!-- 备注等信息 -->
		<view class="card">
			<u-cell isLink :border="false" @click="goRemark">
				<u-text slot="title" text="备注" bold size="14px"></u-text>
				<u-text lines="1" style="width: 120px;" class="u-flex-row-end" slot="value" :text="order.remark || '口味、偏好等要求'" size="14px" color="#999"></u-text>
			</u-cell>
			<u-cell isLink :border="false" @click="showTablewareNum = true">
				<u-text slot="title" text="餐具数量" bold size="14px"></u-text>
				<u-text class="u-flex-row-end" slot="value" :text="order.tablewareNum || '未选择'" size="14px" color="#999"></u-text>
			</u-cell>
		</view>

		<u-action-sheet title="选择收货地址" round="8px" :show="showChooseAddress" @close="showChooseAddress = false">
			<scroll-view>
				<view class="addr-list-wrap">
					<u-radio-group placement="column" v-model="addressActive"  @change="onChangeChooseAddr">
						<view v-for="(item,index) in addressList" :key="item.id">
							<view class="address-wrap" @click="onChangeChooseAddr(index)">
								<u-radio :name="index"></u-radio>
								<AddressItemShow style="width: 95%;" :data="item">
									<u-icon name="edit-pen" size="22" @click="goEditAddress(item.id)"></u-icon>
								</AddressItemShow>
							</view>
							<u-divider></u-divider>
						</view>
					</u-radio-group>
				</view>
			</scroll-view>
			<u-button type="warning" @click="goEditAddress('')">添加收货地址</u-button>
		</u-action-sheet>

		<view class="statistics-bar">
			<cn-money :money="totalPrice" color="#fff" size="25px" iconNormalSize></cn-money>
			<u-button class="purchase-btn" type="warning" @click="addOrder">提交订单</u-button>
		</view>


		<u-picker :show="showTablewareNum" :columns="columns" closeOnClickOverlay @cancel="showTablewareNum = false"
			@confirm="e => {order.tablewareNum = e.value[0]; showTablewareNum = false}"></u-picker>

	</view>
</template>

<script>
	import AddressItemShow from '@/components/AddressItemShow.vue'
	import CnMoney from '@/components/cn-money/cn-money.vue'

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
				sellerId: '',
				order: {
					remark: '',
					//餐具数量
					tablewareNum: '',
				},
				//是否显示餐具数量
				showTablewareNum: false,
				columns: [
					['1份', '2份', '3份', '4份', '5份', '6份', '7份', '8份', '9份', '10份以上']
				],
				//优惠卷列表
				couponList: [],
				//活动列表
				activityList: [],
				//当前使用的优惠卷id
				couponId: ''
			}
		},
		components: {
			AddressItemShow,
			CnMoney
		},
		methods: {
			addOrder() {
				if (this.addressList.length > 0) {
					this.$q({
						url: '/api/takeout/order/create',
						needToken: true,
						method: 'POST',
						data: Object.assign(this.order, {
							addrId: this.addressList[this.addressActive].id,
							sellerId: this.seller.id,
							couponId: this.couponId
						})
					}).then(res => {
						uni.redirectTo({
							url: '/pages/order/pay?orderNo=' + res.data.orderNo
						})
					})
				} else {
					uni.showToast({
						icon: 'none',
						title: '请先添加收货地址',
					})
				}
			},
			goEditAddress(id) {
				uni.navigateTo({
					url: '/pages/address/address?id=' + id
				})
			},
			async onChangeChooseAddr(e) {
				this.addressActive = e
				this.showChooseAddress = false
				await this.deliveryTimeAndPrice()
				this.updateStatisticsData()
			},
			// 更新总价
			updateStatisticsData(){
				// 计算总价
				this.totalPrice = this.seller.packageFee + this.seller.deliveryFee +
					this.settlementGoodsList.map(item => item.goods.price).concat([0, 0]).reduce((a, b) => a + b)
				
				//满减活动条件总价 不包含配送费
				let totalPriceCondition = this.totalPrice - this.seller.deliveryFee
				// 设置满减信息
				let activities = this.activityList.filter(item => totalPriceCondition > item.condition)
				activities.forEach(item => {
					// 红包减免
					if (item.type === 1) {
						item.finalPrice = this.totalPrice - item.reduce
					} else if (item.type === 2) {
						item.finalPrice = this.totalPrice * item.reduce
					}
				})
				activities = activities.sort((a, b) => a.finalPrice - b.finalPrice)
				if (activities.length > 0) {
					this.seller.discount = this.totalPrice - activities[0].finalPrice
					this.totalPrice = activities[0].finalPrice
				}
				
				//外卖红包优惠
				let coupons = this.couponList.filter(item => totalPriceCondition > item.condition).map(item => item.price)
					.sort((a, b) => b - a)
				if (coupons.length > 0) {
					this.seller.coupon = coupons[0]
					this.totalPrice -= coupons[0]
					let thisCoupon = this.couponList.find(item => item.price === coupons[0])
					this.couponId = thisCoupon.id
				}
			},
			async deliveryTimeAndPrice() {
				let res = await this.$q({
					url: '/api/takeout/address/deliveryTime',
					data: {
						addrId: this.addressList[this.addressActive].id,
						sellerId: this.sellerId
					},
					needToken: true
				})
				this.seller.deliveryTime = res.data.deliveryTime
				this.seller.deliveryFee = res.data.deliveryFee
				this.$forceUpdate()
			},
			async init() {
				let res = await Promise.all([
					//获取满减列表
					this.$q({
						url: '/api/takeout/activity/list',
						data: {
							sellerId: this.sellerId
						}
					}),
					//获取店铺红包列表
					this.$q({
						url: '/api/takeout/coupon/list',
						data: {
							sellerId: this.sellerId
						},
						needToken: true
					}),
					//获取购物车信息
					this.$q({
						url: '/api/takeout/basket/listBySeller',
						data: {
							sellerId: this.sellerId,
						},
						needToken: true
					}),
					this.deliveryTimeAndPrice()
				])

				this.settlementGoodsList = res[2].data.rows.map(basket => {
					let descriptionArr = []
					if (basket.skuIdGroup) {
						let skuIds = JSON.parse(basket.skuIdGroup)
						// 通过skuIds.indexOf 重复的id不会展示 item.children.find 同一个分类下只会查找一个
						descriptionArr = basket.goods.skus.map(item => item.children.find(el => skuIds.indexOf(
								el.id) !== -1))
							.map(item => item.name)
					}
					basket.goods.description = descriptionArr.join('、')
					basket.goods.price *= basket.quantity
					return basket
				})

				// 计算打包费
				this.seller.packageFee = this.settlementGoodsList.map(item => item.quantity * item.goods.packingFee)
					.filter(item => item)
					.concat([0, 0]).reduce((a, b) => a + b)
					
				this.activityList = res[0].data.rows
				this.couponList = res[1].data.rows
				
				this.updateStatisticsData()
			},
			goRemark(){
				uni.$on("setRemark", res => {
				   this.order.remark = res
				   uni.showToast({
				   	   icon:'none',
					   title: '已成功添加备注'
				   })
				    // 清除监听
				    uni.$off('setRemark');
				})
				uni.navigateTo({
					url: '/pages/order/remark'
				})
			},
			loadAddressList(){
				this.$q({
					url: '/api/takeout/address/list',
					needToken: true
				}).then(res =>{
					res.data.rows.forEach(item =>{
						let find = this.addressList.find(el => el.id === item.id)
						if(!find){
							this.addressList.push(item)
						}
					})
				})
			}
		},
		watch: {
			showChooseAddress(newVal, oldVal){
				if(newVal){
					this.loadAddressList()
				}
			}
		},
		onLoad(options) {
			this.sellerId = options.id

			//获取商家基础信息
			this.$q({
				url: '/api/takeout/seller/baseInfo/' + this.sellerId
			}).then(res => {
				this.seller = res.data
			})

			uni.getLocation({
				type: 'wgs84',
				geocode: true,
				success: (res) => {
					// 获取推荐地址
					this.$q({
						url: '/api/takeout/address/proposal',
						needToken: true,
						data: {
							myLocation: encodeURI([res.latitude, res.longitude].join(','))
						}
					}).then(res => {
						this.addressList.push(res.data)
						this.init()
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

<style lang="scss">
	.card {
		background-color: #fff;
		margin: 10px auto;
		width: 95%;
		border-radius: 8px;
		box-shadow: 0 0 8px #eee;
		border: 1px solid #eee;
		overflow: hidden;
	}
	
	.addr-list-wrap{
		max-height: 350px;
		padding-bottom: 80px;
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

	.purchase-btn {
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

	.goods-card {
		padding: 10px 15px;

		.goods-item {
			margin: 10px 0;

			.goods-content-wrap {
				width: 80%;

				.goods-cover {
					width: 55px;
					height: 55px;
					border-radius: 5px;
					margin-right: 8px;
				}

				.goods-desc-wrap {
					width: calc(100% - 63px);
				}
			}
		}
	}
	

</style>
