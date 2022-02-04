<template>
	<view>
		<u-tabs :list="menuList" lineColor="#fccf6a" :current="current" @change="onChange"></u-tabs>
		<u-gap height="5px"></u-gap>
		
		<view class="card" v-for="(item,index) in orderList">
			<view class="wrap">
				<u-gap height="20px"></u-gap>
				<view class="u-flex">
					<view class="u-flex u-flex-row-start" v-if="item.status === 2">
						<u-text text="立即送达 /" size="14px" color="#999"></u-text>
						<u-text :text="item.arriveTime" size="14px" color="#333" margin="0 5px"></u-text>
						<u-text text="前送达" size="14px" color="#999"></u-text>
					</view>
					<u-text v-if="item.status === 1" text="待支付" size="20px"></u-text>
					<u-text v-else-if="item.status === 5" text="退款中" size="20px"></u-text>
					<u-text v-else-if="item.status === 2 && getCurrentDelivery(item).sellerStatus === 1" text="待出餐" size="20px"></u-text>
					<u-text v-else-if="item.status === 2 && getCurrentDelivery(item).riderStatus === 1" text="待骑手接单" size="20px"></u-text>
					<u-text v-else-if="item.status === 2 && getCurrentDelivery(item).riderStatus === 2" text="待发起配送" size="20px"></u-text>
					<u-text v-else-if="item.status === 2 && getCurrentDelivery(item).riderStatus === 3" text="配送中" size="20px"></u-text>
				</view>
				<u-gap height="20px"></u-gap>
				<u-line dashed></u-line>
				<u-gap height="20px"></u-gap>
				<view class="u-flex u-flex-col-start">
					<view>
						<view class="u-flex">
							<u-text :text="item.consignee" size="24px" color="#000" bold></u-text>
							<u-text
								:text="'尾号' + item.consigneePhone.substring(item.consigneePhone.length - 4)"
								size="14px" color="#333" margin="0 5px"></u-text>
						</view>
						<u-gap height="6px"></u-gap>
						<u-icon class="u-flex-row-end" name="arrow-right" labelPos="left" space="0" label="点击查看配送地址"
							labelSize="14px" size="14px" color="#999" labelColor="#999"></u-icon>
						<!-- <u-text text="点击查看配送地址" color="#999"></u-text> -->
					</view>
					<u-icon name="phone-fill" size="34px" @click="callPhone(item.consigneePhone)"></u-icon>
				</view>
				<u-gap height="20px"></u-gap>
				<u-line dashed></u-line>
				<view v-if="getDeliveryByStatus(item, 2)">
					<u-gap height="20px"></u-gap>
					<u-text text="出餐完成" bold size="24px" color="#000"></u-text>
					<u-text :text="'{0}完成出餐'.format(getDeliveryByStatus(item, 2).currentTime)" size="14px" margin="5px 0" color="#999"></u-text>
					<u-gap height="20px"></u-gap>
					<u-line dashed></u-line>
				</view>
				<view v-if="getDeliveryByStatus(item, null, 2)">
					<u-gap height="20px"></u-gap>
					<view>
						<view class="u-flex u-flex-row-start">
							<u-text :text="getCurrentDelivery(item).riderName" bold size="24px"></u-text>
							<u-tag text="骑手" type="warning" size="mini" plain plainFill style="margin-left: 5px;"></u-tag>
						</view>
						<view v-if="getDeliveryByStatus(item, null, 3)">
							<u-gap height="6px"></u-gap>
							<u-icon class="u-flex-row-end" name="arrow-down" labelPos="left" space="0" :label="'{0} 骑手已取餐'.format(getDeliveryByStatus(item, null, 3).currentTime)"
								labelSize="14px" size="14px" color="#999" labelColor="#999"></u-icon>
						</view>
					</view>
					<u-gap height="20px"></u-gap>
					<u-line dashed></u-line>
				</view>
				<u-gap height="20px"></u-gap>
				<view>
					<u-text :text="'{0}件商品'.format(item.goodsItemList.length)" bold size="24px" color="#000"></u-text>
					<u-gap height="8px"></u-gap>
					<view>
						<view class="u-flex" v-for="(goods,index) in item.goodsItemList">
							<u-text :text="goods.name" lines="1" style="width: 60%;"></u-text>
							<u-text :text="'x{0}'.format(goods.quantity)"></u-text>
							<u-text :text="goods.total" mode="price"></u-text>
							<u-gap height="6px"></u-gap>
						</view>
					</view>
				</view>
				<u-gap height="20px"></u-gap>
			</view>
			<view v-if="item.status === 2 && getCurrentDelivery(item).sellerStatus === 1">
				<u-gap height="10px"></u-gap>
				<u-button type="success" @click="confirmEatOut(item.code)">确认出餐</u-button>
			</view>
		</view>
		
		<view class="card" v-show="status === 'loading'">
			<view class="wrap">
				<u-gap height="15px"></u-gap>
				<u-skeleton rows="5"></u-skeleton>
				<u-gap height="15px"></u-gap>
			</view>
		</view>
		
		<u-empty mode="list" v-show="orderList.length === 0 && loadStatus === 2"></u-empty>
		
		<u-loadmore :status="status" @loadmore="loadMore" v-show="orderList.length > 0"></u-loadmore>
		<u-gap height="30px"></u-gap>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				originData: {},
				orderList: [],
				current: 0,
				pageInfo: {
					pageNum: 1,
					pageSize: 6
				},
				menuList: [{
						name: '进行中'
					},
					{
						name: '新订单'
					},
					{
						name: '退款'
					},
					{
						name: '待出餐'
					}
				],
				status: 'loadmore',
				loadStatus: 1
			};
		},
		onLoad() {
			this.onChange({
				index: this.current
			})
		},
		methods: {
			getCurrentDelivery(item){
				if(item.deliveryInfoList.length){
					return item.deliveryInfoList[0]
				}else {
					return {}
				}
			},
			getDeliveryByStatus(item, sellerStatus, riderStatus){
				if(sellerStatus){
					return item.deliveryInfoList.find(item => item.sellerStatus === sellerStatus)
				}else if(riderStatus){
					return item.deliveryInfoList.find(item => item.riderStatus === riderStatus)
				}
			},
			callPhone(phoneNum){
				uni.makePhoneCall({
					phoneNumber: phoneNum
				})
			},
			onChange(e){
				this.current = e.index
				let currentList = this.originData[this.current]
				if (currentList) {
					this.orderList = currentList
				} else {
					this.originData[this.current] = []
					this.loadData()
				}
			},
			async loadData(){
				this.status = 'loading'
				this.loadStatus = 1
				let res = await this.$q({
					url: '/seller/takeout/order/list',
					needToken: true,
					data: {
						status: this.current + 1,
						...this.pageInfo
					}
				})
				this.originData[this.current].push(...res.data.rows)
				this.orderList = this.originData[this.current]
				if(this.orderList.length >= res.data.total){
					this.status = 'nomore'
				}else {
					this.status = 'loadmore'
				}
				this.loadStatus = 2
			},
			loadMore(){
				this.pageInfo.pageNum ++
				this.loadData()
			},
			confirmEatOut(code){
				this.$q({
					url: '/seller/takeout/order/eat-out/' + code,
					needToken: true
				}).then(res =>{
					
				})
			}
		},
		async onPullDownRefresh() {
			this.originData[this.current] = []
			this.pageInfo.pageNum = 1
			await this.loadData()
			uni.stopPullDownRefresh()
		},
		onReachBottom() {
			if(this.status === 'loadmore'){
				this.loadMore()
			}
		}
	}
</script>

<style lang="scss">
	.card {
		width: 95%;
		margin-bottom: 6px;
		padding: 0;
		overflow: hidden;
	}

	.wrap {
		padding: 0 15px;
	}
</style>
