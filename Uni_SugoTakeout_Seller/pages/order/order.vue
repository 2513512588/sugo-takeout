<template>
	<view>
		<u-tabs :list="menuList" lineColor="#fccf6a" :current="current"></u-tabs>
		<u-gap height="5px"></u-gap>
		<view class="card" v-for="(item,index) in orderList">
			<view class="wrap">
				<u-gap height="20px"></u-gap>
				<view class="u-flex">
					<view class="u-flex u-flex-row-start">
						<u-text text="立即送达 /" size="14px" color="#999"></u-text>
						<u-text :text="item.arriveTime" size="14px" color="#333" margin="0 5px"></u-text>
						<u-text text="前送达" size="14px" color="#999"></u-text>
					</view>
					<u-text text="待发起配送" size="20px"></u-text>
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
				<u-gap height="20px"></u-gap>
				<view>
					<u-text text="出餐完成" bold size="24px" color="#000"></u-text>
					<u-text :text="'{0}完成出餐'.format(item.arriveTime)" size="14px" margin="5px 0" color="#999"></u-text>
				</view>
				<u-gap height="20px"></u-gap>
				<u-line dashed></u-line>
				<u-gap height="20px"></u-gap>
				<view>
					<view class="u-flex u-flex-row-start">
						<u-text :text="item.deliveryInfoList[item.deliveryInfoList.length - 1].riderName" bold size="24px"></u-text>
						<u-tag text="骑手" type="warning" size="mini" plain plainFill style="margin-left: 5px;"></u-tag>
					</view>
					<u-gap height="6px"></u-gap>
					<u-icon class="u-flex-row-end" name="arrow-down" labelPos="left" space="0" :label="'{0} 骑手已取餐'"
						labelSize="14px" size="14px" color="#999" labelColor="#999"></u-icon>
				</view>
				<u-gap height="20px"></u-gap>
				<u-line dashed></u-line>
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
		</view>
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
				]
			};
		},
		onLoad() {
			let currentList = this.originData[this.current]
			if (currentList) {
				this.orderList = currentList
			} else {
				this.$q({
					url: '/seller/takeout/order/list',
					needToken: true,
					data: {
						status: this.current + 1,
						...this.pageInfo
					}
				}).then(res => {
					console.log(res);
					this.originData[this.current] = res.data.rows
					this.orderList = res.data.rows
				})
			}
		},
		methods: {
			callPhone(phoneNum){
				uni.makePhoneCall({
					phoneNumber: phoneNum
				})
			}
		}
	}
</script>

<style lang="scss">
	.card {
		width: 95%;
		margin-bottom: 6px;
	}

	.wrap {
		padding: 0 15px;
	}
</style>
