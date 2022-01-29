<template>
	<view>
		<u-gap height="8px"></u-gap>
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
				<view>
					<view v-for="(goods,index) in order.orderItemList" :key="goods.id">
						<view class="goods-header">
							<image style="width: 80px; height: 80px; border-radius: 5px;" :src="goods.cover"
								mode="aspectFill"></image>
							<p>
								<u-text :text="goods.name" color="#999" size="13"></u-text>
								<u-text :text="'x' + goods.quantity" color="#999" size="13"></u-text>
							</p>
						</view>
						<u-gap height="4"></u-gap>
					</view>
				</view>
				<u-text :text="'共'+(order.orderItemList && order.orderItemList.length)+'件'" color="#999" size="13"
					align="center" style="word-wrap: break-word; width:13px;"></u-text>
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
							<u-text color="#000" :text="order.total" size="18" mode="price"></u-text>
						</p>
					</u-col>
				</u-row>
				<u-gap height="2"></u-gap>
				<!-- <u-text :text="'订单号：' + order.orderInfo.orderNo" size="13" color="#666"></u-text> -->
			</view>
			<u-gap height="5"></u-gap>
			<u-button icon="phone">致电商家</u-button>
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
					<u-text text="商家按餐量提供" color="#333" size="14"></u-text>
				</view>
			</view>

			<u-gap height="10"></u-gap>

		</view>

		<!-- 	<view class="card">
			<u-gap height="5"></u-gap>
			<view class="order-header">
				<u-text text="订单信息" color="#333" size="16" bold ></u-text>
			</view>
			<u-divider></u-divider>
			<u-gap height="3"></u-gap>
		
		
		
			<u-gap height="10"></u-gap>
		
		</view> -->
	</view>
</template>

<script>
	export default {
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
				}
			}
		},
		methods: {
			goSeller(id) {
				uni.navigateTo({
					url: '/pages/shop/shop?id=' + id
				})
			},
		},
		onLoad(options) {
			this.orderNo = options.code
			this.$q({
				url: '/api/takeout/order/detail/' + this.orderNo,
				needToken: true
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

		p {
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
