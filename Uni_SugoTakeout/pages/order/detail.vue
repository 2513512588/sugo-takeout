<template>
	<view>
		
		<!-- 订单退款/超时取消 -->
		<view v-if="order.status === 4 || order.status === 7">
			<u-gap height="22px"></u-gap>
			<view class="wrap">
				<u-icon class="u-flex-row-end" name="arrow-right" labelPos="left" label="订单已取消" bold size="14px" labelSize="24px" labelColor="#000"></u-icon>
			</view>
			<u-gap height="28px"></u-gap>
			<view class="card">
				<u-gap height="5px"></u-gap>
				<u-text :text="order.status === 4 ? '申请退款，订单已取消' : '支付超时，订单已取消'" size="13px"></u-text>
				<u-gap height="15px"></u-gap>
				<u-grid :col="2">
					<u-grid-item>
						<u-icon name="bag" size="28px" color="#000"></u-icon>
						<u-text text="逛逛别家" size="13px" bold color="#000"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="pushpin" size="28px" color="#000"></u-icon>
						<u-text text="再来一单" size="13px" bold color="#000"></u-text>
					</u-grid-item>
				</u-grid>
				<u-gap height="5px"></u-gap>
			</view>
			<u-gap height="5px"></u-gap>
		</view>
		
		<!-- 订单退款中 -->
		<view v-if="order.status === 5">
			<u-gap height="22px"></u-gap>
			<view class="wrap">
				<u-icon class="u-flex-row-end" name="arrow-right" labelPos="left" label="订单退款中" bold size="14px" labelSize="24px" labelColor="#000"></u-icon>
			</view>
			<u-gap height="28px"></u-gap>
			<view class="card">
				<u-gap height="5px"></u-gap>
				<u-text text="等待商家处理中" size="13px" color="#333"></u-text>
				<u-gap height="15px"></u-gap>
				<u-grid :col="2">
					<u-grid-item>
						<u-icon name="clock" size="28px" color="#000"></u-icon>
						<u-text text="退款进度" size="13px" bold color="#000"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="phone" size="28px" color="#000"></u-icon>
						<u-text text="致电卖家" size="13px" bold color="#000"></u-text>
					</u-grid-item>
				</u-grid>
				<u-gap height="5px"></u-gap>
			</view>
			<u-gap height="5px"></u-gap>
		</view>
		
		<!-- 订单完成 -->
		<view v-if="order.status === 6">
			<u-gap height="22px"></u-gap>
			<view class="wrap">
				<u-icon class="u-flex-row-end" name="arrow-right" labelPos="left" label="订单已完成" bold size="14px" labelSize="24px" labelColor="#000"></u-icon>
			</view>
			<u-gap height="28px"></u-gap>
			<view class="card">
				<u-gap height="5px"></u-gap>
				<u-text text="感谢您的支持和信任，期待再次光临" size="13px" color="#333"></u-text>
				<u-gap height="2px"></u-gap>
				<u-text text="温馨提示: 请厉行节约，拒绝浪费" size="12px" color="#999"></u-text>
				<u-gap height="15px"></u-gap>
				<u-grid :col="3">
					<u-grid-item>
						<u-icon name="red-packet" size="28px" color="#000"></u-icon>
						<u-text text="打赏骑手" size="13px" bold color="#000"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="pushpin" size="28px" color="#000"></u-icon>
						<u-text text="再来一单" size="13px" bold color="#000"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="phone" size="28px" color="#000"></u-icon>
						<u-text text="致电卖家" size="13px" bold color="#000"></u-text>
					</u-grid-item>
				</u-grid>
				<u-gap height="5px"></u-gap>
			</view>
			<u-gap height="5px"></u-gap>
		</view>
		
		<!-- 订单待支付 -->
		<view v-if="order.status === 1">
			<u-gap height="22px"></u-gap>
			<view class="wrap u-flex u-flex-row-start">
				<u-text text="等待支付，剩余" bold size="23px" color="#333"></u-text>
				<u-count-down :time="15 * 60 * 1000" format="mm:ss"></u-count-down>
				<u-icon name="arrow-right" bold size="14px" ></u-icon>
			</view>
			<u-gap height="28px"></u-gap>
			<view class="card">
				<u-gap height="5px"></u-gap>
				<view class="u-flex u-flex-row-start">
					<u-text text="预计" size="16px" color="#333" bold></u-text>
					<u-text text="17:16" size="16px" color="#f7a854" bold margin="0 3px"></u-text>
					<u-text text="送达" size="16px" color="#333" bold></u-text>
				</view>
				<u-gap height="15px"></u-gap>
				<u-grid :col="4">
					<u-grid-item>
						<u-icon name="rmb-circle" size="28px" color="#f7a854"></u-icon>
						<u-text text="立即支付" size="13px" bold color="#f7a854"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="edit-pen" size="28px" color="#f7a854"></u-icon>
						<u-text text="改订单信息" size="13px" bold color="#f7a854"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="phone" size="28px" color="#000"></u-icon>
						<u-text text="致电卖家" size="13px" bold color="#000"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="close-circle" size="28px" color="#000"></u-icon>
						<u-text text="取消订单" size="13px" bold color="#000"></u-text>
					</u-grid-item>
				</u-grid>
				<u-gap height="5px"></u-gap>
			</view>
			<u-gap height="5px"></u-gap>
		</view>
		
		<!-- 订单待评价 -->
		<view v-if="order.status === 3">
			<u-gap height="22px"></u-gap>
			<view class="wrap u-flex u-flex-row-start">
				<u-icon name="clock" size="28px" :customStyle="{marginRight: '5px'}"></u-icon>
				<u-icon class="u-flex-row-end" name="arrow-right" labelPos="left" label="待评价" bold size="14px" labelSize="24px" labelColor="#000"></u-icon>
			</view>
			<u-gap height="28px"></u-gap>
			<view class="card">
				<u-gap height="5px"></u-gap>
				<u-text text="订单已完成，快来评价吧" size="13px" color="#333"></u-text>
				<u-gap height="15px"></u-gap>
				<view>
					<u-row justify="space-between" >
						<u-col span="5.8">
							<u-button>删除订单</u-button>
						</u-col>
						<u-col span="5.8">
							<u-button>再来一单</u-button>
						</u-col>
					</u-row>
				</view>
				<u-gap height="5px"></u-gap>
			</view>
			<u-gap height="5px"></u-gap>
			
			<view class="card">
				<u-gap height="5px"></u-gap>
				<view class="u-flex" style="align-items: center; flex-direction: column;">
					<u-rate :count="5" size="50" :value="0" @change="toEvaluate"></u-rate>
					<u-gap height="5px"></u-gap>
					<u-text text="感觉如何？给个评价吧!" size="13px" color="#999"></u-text>
				</view>
				<u-gap height="5px"></u-gap>
			</view>
			<u-gap height="5px"></u-gap>
		</view>
		
		<!-- 订单配送中 -->
		<view v-if="order.status === 2">
			<delivery @ready="mapReady" :customStyle="{height: '300px'}" :markers="markers"></delivery>
			<view class="card">
				<u-gap height="5px"></u-gap>
				<view class="u-flex u-flex-row-start">
					<u-text text="预计" size="21px" color="#333" bold></u-text>
					<u-text :text="order.arriveTime" size="21px" color="#f7a854" bold margin="0 3px"></u-text>
					<u-text text="送达" size="21px" color="#333" bold></u-text>
				</view>
				<u-gap height="5px"></u-gap>
				<u-icon name="info-circle" label="以实际情况为准" color="#999" labelColor="#999" size="13px" labelSize="13px"></u-icon>
				<u-gap height="12px"></u-gap>
				<u-button>申请退款</u-button>
				<u-gap height="8px"></u-gap>
				<u-grid :col="3">
					<u-grid-item>
						<u-icon name="chat" size="28px" color="#000"></u-icon>
						<u-text text="致电商家" size="13px" bold color="#000"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="phone" size="28px" color="#000"></u-icon>
						<u-text text="致电骑手" size="13px" bold color="#000"></u-text>
					</u-grid-item>
					<u-grid-item>
						<u-icon name="checkmark-circle" size="28px" color="#000"></u-icon>
						<u-text text="确定收货" size="13px" bold color="#000"></u-text>
					</u-grid-item>
				</u-grid>
				<u-gap height="5px"></u-gap>
			</view>
			<u-gap height="5px"></u-gap>
		</view>
		
		<view class="card">
			<u-gap height="5"></u-gap>
			<view class="u-flex">
				<u-text :text="order.sellerName" color="#333" size="16" bold suffixIcon="arrow-right"
					iconStyle="font-weight: bold; font-size: 13px" @click="goSeller(order.sellerId)"></u-text>
				<u-text :text="orderStatus[order.status]" color="#999" size="13"></u-text>
			</view>
			<u-divider></u-divider>
			<u-gap height="3"></u-gap>
			<view class="goods-wrap">
				<view class="goods-items-wrap">
					<view v-for="(goods,index) in order.orderItemList" :key="goods.id">
						<view class="goods-header">
							<image style="width: 80px; height: 80px; border-radius: 5px;"
								:src="goods.cover" mode="aspectFill"></image>
							<view class="goods-info-wrap">
								<u-text :text="goods.name" color="#999" size="13" lines="1" ></u-text>
								<u-text :text="'x' + goods.quantity" color="#999" size="13"></u-text>
							</view>
						</view>
						<u-gap height="4"></u-gap>
					</view>
				</view>
				<u-text :text="'共'+(order.orderItemList && order.orderItemList.length)+'件'" color="#999" size="13"
					align="right" style="white-space: nowrap;"></u-text>
			</view>
			<u-gap height="10"></u-gap>
			<view class="order-bottom-info">
				<u-row justify="space-between">
					<u-col span="8">
						<!-- <u-text v-if="order.orderInfo.payTime !== null" :text="'下单时间：' + order.orderInfo.payTime" -->
						<!-- color="#666" size="13"></u-text> -->
					</u-col>
					<u-col span="4">
						<p style="display: flex; align-orders: center; justify-content: flex-end;">
							<u-text text="合计" size="13" color="#666"></u-text>
							<u-text color="#000" :text="order.total || 0" size="18" mode="price"></u-text>
						</p>
					</u-col>
				</u-row>
				<u-gap height="2"></u-gap>
			</view>
			<view v-if="order.status === 3">
				<u-gap height="5"></u-gap>
				<u-button icon="phone">致电商家</u-button>
			</view>
			<u-gap height="5"></u-gap>
		</view>

		<u-gap height="5"></u-gap>

		<view class="card">
			<u-gap height="5"></u-gap>
			<view class="order-header">
				<u-text text="配送信息" color="#333" size="16" bold></u-text>
			</view>
			<u-divider></u-divider>
			<u-gap height="3"></u-gap>

			<view class="info-wrap">
				<view class="u-flex">
					<u-text text="期望时间" color="#999" size="14"></u-text>
					<u-text text="立即配送" color="#333" size="14"></u-text>
				</view>
				<u-gap height="5px"></u-gap>
				<view class="u-flex">
					<u-text text="收货人" color="#999" size="14"></u-text>
					<u-text :text="'{0}({1})'.format(order.consignee, sexGroup[order.consigneeSex])" color="#333" size="14"></u-text>
				</view>
				<u-gap height="5px"></u-gap>
				<view class="u-flex">
					<u-text text="手机号" color="#999" size="14"></u-text>
					<u-text :text="order.consigneePhone" color="#333" size="14"></u-text>
				</view>
				<u-gap height="5px"></u-gap>
				<view class="u-flex">
					<u-text text="配送地址" color="#999" size="14"></u-text>
					<u-text :text="order.addrName" color="#333" size="14"></u-text>
				</view>
			</view>

			<u-gap height="10"></u-gap>

		</view>

		<u-gap height="5"></u-gap>

		<view class="card">
			<u-gap height="5"></u-gap>
			<view class="order-header">
				<u-text text="订单信息" color="#333" size="16" bold></u-text>
			</view>
			<u-divider></u-divider>
			<u-gap height="3"></u-gap>

			<view class="info-wrap">
				<view class="u-flex">
					<u-text text="订单号" color="#999" size="14"></u-text>
					<u-text :text="order.code" color="#333" size="14"></u-text>
				</view>
				<u-gap height="5px"></u-gap>
				<view class="u-flex" v-if="order.createTime">
					<u-text text="创建时间" color="#999" size="14"></u-text>
					<u-text :text="order.createTime" color="#333" size="14"></u-text>
				</view>
				<u-gap height="5px" v-if="order.payTime"></u-gap>
				<view class="u-flex" v-if="order.payTime">
					<u-text text="付款时间" color="#999" size="14"></u-text>
					<u-text :text="order.payTime" color="#333" size="14"></u-text>
				</view>
				<u-gap height="5px" v-if="order.payType"></u-gap>
				<view class="u-flex" v-if="order.payType">
					<u-text text="支付方式" color="#999" size="14"></u-text>
					<u-icon v-if="order.payType === 1" name="rmb-circle-fill" label="电子钱包" size="16" labelSize="14"
						color="#f29100">
					</u-icon>
					<u-icon v-if="order.paymentType === 2" name="zhifubao-circle-fill" label="支付宝支付" size="20"
						labelSize="14" color="rgb(41, 121, 255)"></u-icon>
					<u-icon v-else-if="order.paymentType === 3" name="weixin-circle-fill" label="微信支付" size="20"
						labelSize="14" color="green">
					</u-icon>
					<u-icon v-else-if="order.paymentType === 'apple-wallet'" name="apple-fill" label="Apple钱包" size="20"
						labelSize="14" color="#000">
					</u-icon>
				</view>
				<u-gap height="5px"></u-gap>
				<view class="u-flex">
					<u-text text="餐具数量" color="#999" size="14"></u-text>
					<u-text :text="order.tablewareNum || '商家按餐量提供'" color="#333" size="14"></u-text>
				</view>
			</view>

			<u-gap height="10"></u-gap>

		</view>


	</view>
</template>

<script>
	
	import delivery from '@/components/ITkoala-amap/delivery.vue'
	
	export default {
		components: {
			delivery
		},
		data() {
			return {
				order: {

				},
				orderNo: '',
				orderStatus: {
					1: '待支付',
					2: '已支付',
					3: '完成',
					4: '取消',
					5: '退款中',
					6: '已评价'
				},
				sexGroup: {
					1: '先生',
					2: '女士'
				},
				markers: [
					
				],
			}
		},
		methods: {
			goSeller(id) {
				uni.navigateTo({
					url: '/pages/shop/shop?id=' + id
				})
			},
			toEvaluate(){
				uni.switchTab({
					url: '/pages/index/index'
				})
			},
			async mapReady(){
				let res = await this.$q({
					url: '/api/takeout/order/map/info',
					data: {
						code: this.orderNo
					}
				})
				this.markers.push({
					location: {
						lat: res.data.sellerLocation[0],
						lng: res.data.sellerLocation[1]
					},
					role: 2,
					logo: res.data.sellerLogo
				})
				this.markers.push({
					location: {
						lat: res.data.customLocation[0],
						lng: res.data.customLocation[1]
					},
					role: 3
				})
				if(this.order.status === 2){
					this.loadRealStatus(false)
					setInterval(() =>{
						this.loadRealStatus(false)
					}, 15000)
				}
			},
			async loadRealStatus(loading){
				let res = await this.$q({
					url: '/api/takeout/delivery/real-status',
					data: {
						orderCode: this.orderNo
					},
					needToken: true,
					loading: loading
				})
				if(res.data.riderLocation){
					let locationArr = res.data.riderLocation.split(',')
					let location = {
						lat: locationArr[0],
						lng: locationArr[1]
					}
					let rider = this.markers.find(item => item.role === 1)
					if(rider){
						rider.location = location
					}else {
						this.markers.push({
							location: location,
							role: 1
						})
					}
				}
				let rider = this.markers.find(item => item.role === 1)
				if(rider){
					if(res.data.delivery.riderStatus === 4){
						rider.message = '已送达'
					}else {
						if(res.data.delivery.riderStatus === 2){
							rider.message = '骑手正赶往商家'
						}else if(res.data.delivery.riderStatus === 3){
							rider.message = '骑手已取餐，配送中'
						}
						if(res.data.distance){
							rider.distance = (res.data.distance / 1000).toFixed(1)
						}
						if(res.data.duration){
							rider.duration = res.data.duration
						}
						if(res.data.arriveTime){
							this.order.arriveTime = res.data.arriveTime
						}
					}
				}
				let seller = this.markers.find(item => item.role === 2)
				if(seller){
					if(res.data.delivery.sellerStatus === 1){
						seller.message = '待商家出餐'
					}else if(res.data.delivery.sellerStatus === 2){
						seller.message = '商家已出餐'
					}
				}
			}
		},
		onLoad(options) {
			this.orderNo = options.code
			this.$q({
				url: '/api/takeout/order/detail/' + this.orderNo,
				needToken: true,
			}).then(res => {
				this.order = res.data
			})
		}
	}
</script>

<style lang="scss">
	.card {
		padding: 8px 12px;
	}

	.goods-wrap {
		display: flex;
		align-items: center;
		justify-content: space-between;
		
		.goods-items-wrap{
			width: 80%;
		}
	}

	.order-header {
		display: flex;
		align-items: flex-start;
		justify-content: space-between;
	}

	.goods-header {
		display: flex;
		align-items: center;
		justify-content: flex-start;

		.goods-info-wrap {
			width: 60%;
			margin-left: 8px;
		}
	}

	.operation-wrap {
		.u-button {
			width: 60px;
			height: 30px;
		}
	}

	.u-divider {
		margin: 10px 0 !important;
	}
	
	.u-count-down {
		::v-deep .u-count-down__text{
			font-size: 23px;
			color: #f7a854;
			font-weight: bold;
			width: 3em;
			text-align: center;
		}
	}
</style>
