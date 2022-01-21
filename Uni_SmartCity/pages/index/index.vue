<template>
	<view class="takeout">

		<u-navbar safeAreaInsetTop fixed placeholder height="55px" bgColor="#FFD000">
			<u-icon slot="left" labelPos="left" bold name="arrow-down" :size="18" :label="address.address && address.address.city" color="#333"
				labelColor="#333"></u-icon>
			<view slot="center">
				<u-search :animation="true" v-model="keyword" disabled></u-search>
			</view>
			<view class="u-flex" slot="right">
				<u-icon name="mic" size="23px" bold color="#333"></u-icon>
				<u-icon name="bell-fill" bold size="23px" color="#333" style="margin-left: 8px;"></u-icon>
			</view>
		</u-navbar>


		<u-swiper :list="carouselList" height="350rpx" indicator indicatorMode="dot"
			radius="0"></u-swiper>

		<u-gap height="5px"></u-gap>
		<view class="menu card">
			<u-grid :border="false" :col="4">
				<u-grid-item v-for="(item, index) in menuList" :key="index">
					<u-icon :customStyle="{paddingTop:20+'rpx'}" :name="item.icon" size="42px"></u-icon>
					<text class="grid-text">{{item.name}}</text>
				</u-grid-item>
			</u-grid>
		</view>

		<u-tabs :list="tabsList" @change="loadData" lineColor="#FFD000"></u-tabs>

		<WaterfallFlow v-if="sellerList.length > 0 && current === 2" :list="sellerList" coverKeyName="avatar" primaryKeyName="id" @clickItem="goSellerDetail">
			<template v-slot:item="{item}">
				<view class="seller_body">
					<u-text :text="item.name" color="#333" size="15" bold></u-text>
					<view class="seller_info tags_wrap">
						<span>{{item.additionalData.avgScore}}分</span>
						<span>月订单{{item.additionalData.monthOrderNum}}</span>
						<span>{{(item.additionalData.distance / 1000).toFixed(1)}}km</span>
					</view>
					<view class="seller_info">
						<cn-money :money="item.additionalData.avgCost" color="#f76343" size="18" iconNormalSize></cn-money>
						<u-tag size="mini" plain :text="'配送时间' + (item.additionalData.avgDeliveryTime) + '分'" type="warning"
							color="#f76343" style="margin-left: auto; transform-origin: right center; transform: scale(.8);"></u-tag>
					</view>
					<u-gap height="3px"></u-gap>
				</view>
			</template>
		</WaterfallFlow>
		
		<WaterfallFlow v-if="sellerList.length > 0 && current !== 2" :list="sellerList" coverKeyName="cover" primaryKeyName="sellerId" @clickItem="goSellerDetail">
			<template v-slot:item="{item}">
				<view class="seller_body">
					<u-text :text="item.name" color="#333" size="15" bold></u-text>
					<view class="seller_info tags_wrap">
						<span>{{item.additionalData.avgScore}}分</span>
						<span>月销{{item.additionalData.monthOrderNum}}单</span>
						<span>{{(item.additionalData.distance / 1000).toFixed(1)}}km</span>
					</view>
					<view class="seller_info">
						<cn-money :money="item.price" color="#f76343" size="18" iconNormalSize></cn-money>
						<u-tag size="mini" plain :text="'配送时间' + (item.additionalData.avgDeliveryTime) + '分'" type="warning"
							color="#f76343" style="margin-left: auto; transform-origin: right center; transform: scale(.8);"></u-tag>
					</view>
					<u-gap height="3px"></u-gap>
				</view>
			</template>
		</WaterfallFlow>

	</view>
</template>

<script>
	import WaterfallFlow from '@/components/WaterfallFlow.vue'
	import CnMoney from '@/components/cn-money/cn-money.vue';	
	
	export default {
		data() {
			return {
				carouselList: [
					'https://cdn.uviewui.com/uview/swiper/swiper1.png',
					'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
				],
				menuList: [],
				sellerList: [],
				orginData: {},
				address: {},
				keyword: '',
				pageInfo: {
					pageNum: 1,
					pageSize: 20
				},
				tabsList: [{
						name: '猜你喜欢'
					},{
						name: '今日特价'
					},{
						name: '发现好店'
					}					
				],
				current: 0
			}
		},
		components: {
			WaterfallFlow,
			CnMoney
		},
		methods: {
			loadData(e){
				let index = this.current = e.index
				if(this.orginData[index]){
					this.sellerList = []
					this.$nextTick(()=>{
						this.sellerList = this.orginData[index]
					})
				}else {
					if(index === 2){  //发现好店
						this.$q({
							url: '/api/takeout/seller/near/list' + uni.$u.queryParams(this.pageInfo),
							method: 'POST',
							data: {
								province: '湖南',
								city: '衡阳',
								myLocation: [this.address.latitude, this.address.longitude].join(',')
							},
						}).then(res => {
							this.sellerList = []
							this.$nextTick(()=>{
								this.sellerList = res.data.rows
								this.orginData[index] = this.sellerList
							})
						})
					}else {
						this.$q({
							url: '/api/takeout/goods/near/list',
							method: 'POST',
							data: {
								province: '湖南',
								city: '衡阳',
								myLocation: [this.address.latitude, this.address.longitude].join(','),
								type: index + 1
							}
						}).then(res =>{
							this.sellerList = []
							this.$nextTick(()=>{
								this.sellerList = res.data.rows
								this.orginData[index] = this.sellerList
							})
						})
					}
				}
			},
			goSellerDetail(id){
				uni.navigateTo({
					url: '/pages/shop/shop?id=' + id
				})
			}
		},
		onLoad() {
			this.$q({
				url: '/api/takeout/seller/type/list',
			}).then(res => {
				this.menuList = res.data.rows
			})
			uni.getLocation({
				type: 'wgs84',
				geocode: true,
				success: (res) => {
					this.address = res
					this.loadData({
						index: this.current
					})
				},
				fail: (e) => {
					console.log(e);
				},
				complete: (e) => {
					console.log(e);
				}
			})
		},
		onPullDownRefresh() {
			uni.stopPullDownRefresh()
			this.loadSellerData()
		}
	}
</script>

<style lang="scss">
	.grid-text {
		font-size: 13px;
		color: #909399;
		padding: 10rpx 0 20rpx 0rpx;
		/* #ifndef APP-PLUS */
		box-sizing: border-box;
		/* #endif */
	}

	.menu {
		width: 100%;
		background-color: #fff;
	}


	.location_wrap {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.location_wrap p {
		font-size: 14px;
		color: #666;
		max-width: 6em;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		text-align: left;
	}



	.seller_body {
		width: 100%;
		padding: 0 7px;
		box-sizing: border-box;

		.seller_info {
			display: flex;
			align-items: center;
			justify-content: flex-start;

			::v-deep .u-tag {
				margin-left: 4px;
				border: 1px solid #f76343 !important;

				.u-tag__text--warning--plain {
					color: #f76343 !important;
				}

				.u-tag--mini {
					height: 18px !important;
					padding: 0 3px !important;
				}
			}
		}

		.tags_wrap {
			font-size: 12px;
			color: #999;

			span {
				display: block;
				margin-right: 10px;
				position: relative;
			}

			span:not(:last-child)::after {
				content: "";
				display: block;
				position: absolute;
				width: 1px;
				height: 50%;
				top: 50%;
				transform: translateY(-50%);
				right: -5px;
				background-color: #999;
			}
		}

	}
	
	// .takeout {
	// 	.u-tabbar {
	// 		::v-deep .u-tabbar__content__item-wrapper{
	// 			height: 55px !important;
	// 		}
	// 		::v-deep .u-icon__icon{
	// 			font-size: 32px !important;
	// 			line-height: 26px !important;
	// 		}
	// 	}
	// }
	
	
</style>
