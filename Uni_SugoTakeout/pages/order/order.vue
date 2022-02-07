<template>
	<view>
		<view class="menu">
			<u-tabs :list="meueList" :current="active" lineColor="#f7df62" @click="onChangeMenu"></u-tabs>
		</view>
		<u-gap height="5"></u-gap>
		<view class="card-wrap" v-show="orderList.length !== 0">
			<view class="card" v-for="(item,index) in orderList" :key="item.code">
				<u-gap height="3"></u-gap>
				<navigator :url="'/pages/shop/shop?id={0}'.format(item.sellerId)">
					<view class="u-flex">
						<u-text :text="item.sellerName" color="#333" size="16" bold suffixIcon="arrow-right"
							iconStyle="font-weight: bold; font-size: 13px"></u-text>
						<u-text v-if="item.status !== 2" :text="orderStatus[item.status]" color="#999" size="13"></u-text>
						<u-text v-else-if="item.status === 2 && item.delivery.sellerStatus === 1" text="待商家出餐" color="#999" size="13"></u-text>
						<u-text v-else-if="item.status === 2 && item.delivery.sellerStatus === 2" :text="riderStatus[item.delivery.riderStatus]" color="#999" size="13"></u-text>
					</view>
				</navigator>
				<u-divider></u-divider>
				<u-gap height="3"></u-gap>
				<view class="goods-wrap" @click="goDetail(item.code)">
					<view class="goods-items-wrap">
						<view v-for="(goods,index) in item.orderItemList" :key="goods.id">
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
					<u-text :text="'共'+ (item.orderItemList.length) +'件'"
						color="#999" size="13" align="right" style="white-space: nowrap;"></u-text>
				</view>
				<u-gap height="10"></u-gap>
				<view class="order-bottom-info">
					<view class="u-flex u-flex-row-end">
						<u-text text="合计" size="13" color="#666" style="white-space: nowrap;"></u-text>
						<u-text color="#000" :text="item.total" size="18" 
							mode="price"></u-text>
					</view>
					<u-text :text="'下单时间：' + item.createTime"
						color="#666" size="13"></u-text>
					<u-gap height="2"></u-gap>
					<u-text :text="'订单号：' + item.code" size="13" color="#666"></u-text>
				</view>
				<u-gap height="15"></u-gap>
				<u-row class="operation-wrap" justify="flex-end" gutter="10">
					<u-col span="2" v-if="[3, 4, 6].contains(item.status)">
						<u-button @click="deleteOrder(item.code)">删除</u-button>
					</u-col>
					<u-col span="2">
						<u-button @click="feeback">反馈</u-button>
					</u-col>
					<u-col span="2" v-if="item.status === 2">
						<u-button @click="refund(item.code)">退款</u-button>
					</u-col>
					<u-col span="2" v-if="item.status === 3">
						<u-button type="warning" @click="evaluate(item.code)">评价</u-button>
					</u-col>
					<u-col span="3" v-if="item.status === 2 && item.delivery.riderStatus === 4" >
						<u-button type="warning" style="width: 82px;" @click="confirmReceiving(item.code)">确定收货</u-button>
					</u-col>
					<u-col span="2" v-if="item.status === 1">
						<navigator :url="'/pages/order/pay?orderNo={0}'.format(item.code)">
							<u-button type="warning" >支付</u-button>
						</navigator>
					</u-col>
				</u-row>
				<u-gap height="5"></u-gap>
			</view>
		</view>

		<u-empty
			v-show="loadingStatus === 2 && orderList.length === 0"
			mode="list"></u-empty>

		<view class="card" v-show="loadingStatus === 1" style="padding: 20px 15px;">
			<u-skeleton :rows="8"></u-skeleton>
		</view>

		<u-loadmore v-show="loadingStatus === 2 && orderList.length > 0" :status="status" @loadmore="loadMore"></u-loadmore>
	
		<u-gap height="8px"></u-gap>
	</view>
</template>

<script>

	export default {
		data() {
			return {
				active: 0,
				meueList: [{
						name: '全部',
						value: ''
					},
					{
						name: '待付款',
						value: 1
					},
					{
						name: '待评价',
						value: 3
					},
					{
						name: '退款/售后',
						value: [4, 5]
					},
				],
				orderStatus: {
					1: '订单已提交',
					2: '已支付',
					3: '待评价',
					4: '已取消',
					5: '退款中',
					6: '已完成'
				},
				riderStatus: {
					1: '待骑手接单',
					2: '待骑手取餐',
					3: '配送中',
					4: '已送达'
				},
				originData: [],
				orderList: [],
				//1加载中 2加载完成 
				loadingStatus: 1,
				status: 'loadmore',
				pageInfo: {
					pageSize: 6,
					pageNum: 1,
				}
			}
		},
		methods: {
			async onChangeMenu(e) {
				this.loadingStatus = 1
				this.active = e.index
				let tempList = this.originData[e.index]
				if (tempList) {
					this.orderList = tempList
				} else {
					let data = await this.loadData()
					this.originData[e.index] = data.rows
					this.orderList = data.rows
				}
				this.loadingStatus = 2
			},
			goDetail(code) {
				uni.navigateTo({
					url: '/pages/order/detail?code=' + code
				})
			},
			refund(orderNo) {
				uni.navigateTo({
					url: '/pages/takeout/refund?orderNo=' + orderNo
				})
			},
			evaluate(orderNo) {
				uni.navigateTo({
					url: '/pages/takeout/evaluate?orderNo=' + orderNo
				})
			},
			feeback() {
				uni.navigateTo({
					url: '/pages/takeout/feedback'
				})
			},
			confirmReceiving(code){
				
			},
			deleteOrder(code) {
				uni.showModal({
					title: '提示',
					content: '是否确定删除？',
					success: (res) => {
						if (res.confirm) {
							this.$q({
								url: '/prod-api/api/movie/ticket/order/' + id,
								method: 'DELETE',
								token: true
							}).then(res => {
								uni.showToast({
									icon: 'success',
									duration: 2000,
									title: '删除成功'
								})
								let index = this.originData.findIndex(item => item.orderInfo.id == id)
								this.originData.splice(index, 1)
							})
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			},
			async loadData() {
				let res = await this.$q({
					url: '/api/takeout/order/list',
					data: Object.assign(this.pageInfo, {
						status: this.meueList[this.active].value
					}),
					needToken: true
				})
				if(this.pageInfo.pageNum >= res.data.pages){
					this.status = 'nomore'
				}else {
					this.status = 'loadmore'
				}
				return res.data
			},
			async loadMore(){
				if(this.status === 'loadmore'){
					this.loadingStatus = 1
					this.status = 'loading'
					this.pageInfo.pageNum ++
					let data = await this.loadData()
					this.orderList.push(...data.rows)
					this.loadingStatus = 2
				}
			}
		},
		onLoad() {
			this.onChangeMenu({
				index: this.active
			})
		},
		onReachBottom() {
			this.loadMore()
		},
		onPullDownRefresh() {
			this.pageInfo.pageNum = 1
			this.loadData()
			uni.stopPullDownRefresh()
		}
	}
</script>

<style lang="scss">
	.menu {
		background-color: #fff;
		padding: 3px 0;
		box-shadow: 0 0 5px #eee;
	}

	.card {
		width: 95%;
		border-radius: 5px;
		margin: 7px auto;
		border: 1px solid #eee;
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
</style>
