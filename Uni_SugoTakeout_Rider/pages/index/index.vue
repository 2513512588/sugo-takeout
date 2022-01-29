<template>
	<view>
		<view class="header">
			<u-navbar bgColor="#262937" leftIcon="list" leftIconColor="#fff" safeAreaInsetTop placeholder fixed>
				<!-- <u-icon slot="center" name="checkbox-mark" label="开工中" color="#20d820" labelColor="#fff" bold></u-icon> -->
				<u-icon slot="center" name="hourglass-half-fill" label="等待上线" color="#cfcfcf" labelColor="#fff" bold
					size="18"></u-icon>
			</u-navbar>
			<u-tabs :list="list" lineColor="#000" :activeStyle="{ color: '#fff'}" :current="current"
				:inactiveStyle="{ color: 'rgba(255, 255, 255, .5)'}" @change="onChange"></u-tabs>
		</view>
		<u-gap height="8px"></u-gap>

		<view class="card" v-if="current === 0 && loadStatus === 2" v-for="(item,index) in orderList">
			<view class="u-flex">
				<view class="u-flex u-flex-row-start">
					<u-icon name="clock" bold size="14px" color="#000"></u-icon>
					<u-text :text="'{0}分钟'.format(item.timestamp / 60)" bold margin="0 5px" size="16px" color="#f48942">
					</u-text>
					<u-text text="送达" bold size="16px"></u-text>
				</view>
				<u-text :text="'{0}元'.format(item.price)" color="#f0371a" size="19px" bold></u-text>
			</view>
			<u-gap height="8px"></u-gap>
			<u-line></u-line>
			<u-gap height="8px"></u-gap>
			<view class="task-body-wrap u-flex u-flex-row-start">
				<view class="time-line-wrap">
					<view class="u-flex distance-wrap">
						<u-text :text="(item.originDistance / 1000).toFixed(1)" color="#333" size="16px" block bold>
						</u-text>
						<u-text text="km" color="#999" size="12px"></u-text>
					</view>
					<u-line direction="col" length="15%"></u-line>
					<view class="u-flex distance-wrap">
						<u-text :text="(item.targetDistance / 1000).toFixed(1)" color="#333" size="16px" block bold>
						</u-text>
						<u-text text="km" color="#999" size="12px"></u-text>
					</view>
				</view>
				<view class="addr-content-wrap">
					<view>
						<u-text :text="item.shopName" lines="1" size="20px" color="#000" bold></u-text>
						<u-text :text="item.originAddrDesc" lines="1" size="13px" color="#999" bold></u-text>
					</view>
					<u-gap height="15px"></u-gap>
					<view>
						<u-text :text="item.targetAddrName" lines="1" size="20px" color="#000" bold></u-text>
					</view>
				</view>
			</view>
			<u-gap height="15px"></u-gap>
			<u-button type="warning" class="u-flex" @click="receive(item.code, index)">
				<u-text text="抢单" color="#000" size="18px" bold></u-text>
			</u-button>
		</view>

		<view class="card" v-if="current === 1 && loadStatus === 2" v-for="(item,index) in orderList">
			<view class="u-flex">
				<view class="u-flex u-flex-row-start">
					<u-icon name="clock" bold size="16px" color="#000"></u-icon>
					<u-text :text="'客户已等{0}分钟({1}前送达)'.format(0, item.arriveTime)" bold margin="0 5px" size="16px"
						color="#333">
					</u-text>
				</view>
				<u-text :text="'{0}元'.format(item.price)" color="#f0371a" size="19px" bold></u-text>
			</view>
			<u-gap height="8px"></u-gap>
			<u-line></u-line>
			<u-gap height="8px"></u-gap>
			<u-row justify="space-between" align="top">
				<u-col span="11">
					<u-steps current="0" direction="column" dot activeColor="#f9c932">
						<u-steps-item class="active" :title="item.shopName" :desc="item.sellerAddrDesc"></u-steps-item>
						<u-steps-item :title="item.consigneeAddrName"></u-steps-item>
					</u-steps>
				</u-col>
				<u-col span="1">
					<u-icon name="map" size="25" color="#999" bold style="margin: 3px 0;" @click="navigation(item.sellerLatLng, item.shopName)"></u-icon>
				</u-col>
			</u-row>
			<u-gap height="8px"></u-gap>
			<u-row justify="space-between">
				<u-col span="3.6">
					<u-button icon="phone" @click="callPhone(item.sellerPhone)">联系</u-button>
				</u-col>
				<u-col span="8">
					<u-button type="warning" class="u-flex" @click="confirmPickup(item.code, index)">
						<u-text text="确认已取货" color="#000" bold></u-text>
					</u-button>
				</u-col>
			</u-row>
		</view>


		<view class="card" v-if="current === 2 && loadStatus === 2" v-for="(item,index) in orderList">
			<view class="u-flex">
				<view class="u-flex u-flex-row-start">
					<u-icon name="clock" bold size="16px" color="#000"></u-icon>
					<u-text :text="'还剩{0}分钟送达'.format(item.arriveTime)" bold margin="0 5px" size="16px" color="#333">
					</u-text>
				</view>
				<u-text :text="'{0}元'.format(item.price)" color="#f0371a" size="19px" bold></u-text>
			</view>
			<u-gap height="8px"></u-gap>
			<u-line></u-line>
			<u-gap height="8px"></u-gap>
			<u-row justify="space-between" align="bottom">
				<u-col span="11">
					<u-steps current="1" direction="column" dot activeColor="#f9c932">
						<u-steps-item :title="item.shopName" :desc="item.sellerAddrDesc"></u-steps-item>
						<u-steps-item class="active" :title="item.consigneeAddrName"
							:desc="'{0}({1})'.format(item.consigneeAddrDesc, item.consigneeAddrHouseNumber)">
						</u-steps-item>
					</u-steps>
				</u-col>
				<u-col span="1">
					<u-icon name="map" size="25" color="#999" bold style="margin: 5px 0;"></u-icon>
				</u-col>
			</u-row>
			<u-gap height="8px"></u-gap>
			<u-row justify="space-between">
				<u-col span="3.6">
					<u-button icon="phone" @click="callPhone(item.consigneePhone)">联系</u-button>
				</u-col>
				<u-col span="8">
					<u-button type="success" class="u-flex" @click="confirmRecevi(item.code, index)">
						<u-text text="送达至客户" color="#fff" bold></u-text>
					</u-button>
				</u-col>
			</u-row>
		</view>


		
		<view class="card" v-show="status === 'loading'">
			<u-skeleton rows="5"></u-skeleton>
		</view>

		<u-empty mode="list" v-show="orderList.length === 0 && loadStatus === 2"></u-empty>

		<u-loadmore :status="status" v-show="orderList.length > 0" @loadmore="loadMore"></u-loadmore>

		<u-gap height="20"></u-gap>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [{
						name: '新任务'
					},
					{
						name: '待取货',
						badge: {
							value: 0,
						}
					},
					{
						name: '配送中',
						badge: {
							value: 0,
						}
					}
				],
				current: 0,
				originDataList: {

				},
				address: {},
				orderList: [],
				pageInfo: {
					pageNum: 1,
					pageSize: 6
				},
				status: 'loadmore',
				// 1 未加载 2 已加载
				loadStatus: 1
			}
		},
		onLoad() {
			uni.getLocation({
				type: 'wgs84',
				geocode: true,
				success: (res) => {
					this.address = res
					this.onChange({
						index: this.current
					})
				},
				fail: (e) => {
					console.log(e);
				}
			})
		},
		onShow() {
			// uni.removeStorageSync('token')
			if (!uni.getStorageSync('token')) {
				uni.navigateTo({
					url: '/pages/login/login'
				})
			}
		},
		methods: {
			async receive(code, index) {
				await this.$q({
					url: '/rider/takeout/order/receive/' + code,
					needToken: true
				}).then(res => {
					uni.showToast({
						title: '抢单成功'
					})
				}).catch(err => {
					uni.showToast({
						icon: 'none',
						title: '订单已被别人抢走了'
					})
				})
				this.originDataList[this.current].splice(index, 1)
			},
			async loadData() {
				this.loadStatus = 1
				this.status = 'loading'
				let res
				if (this.current === 0) {
					res = await this.$q({
						url: '/rider/takeout/order/list',
						data: {
							location: [this.address.latitude, this.address.longitude].join(','),
							...this.pageInfo
						},
					})
				} else {
					res = await this.$q({
						url: '/rider/takeout/order/accepted/list',
						data: {
							status: this.current,
							...this.pageInfo
						},
						needToken: true
					})
					this.list[this.current].badge.value = res.data.total
				}
				this.originDataList[this.current].push(...res.data.rows)
				this.orderList = this.originDataList[this.current]

				if (this.orderList.length >= res.data.total) {
					this.status = 'nomore'
				} else {
					this.status = 'loadmore'
				}
				this.loadStatus = 2
			},
			onChange(e) {
				this.current = e.index
				let orderList = this.originDataList[this.current]
				if (orderList) {
					this.orderList = orderList
				} else {
					this.originDataList[this.current] = []
					this.loadData()
				}
			},
			loadMore() {
				this.pageInfo.pageNum++
				this.loadData()
			},
			callPhone(phone) {
				uni.makePhoneCall({
					phoneNumber: phone
				})
			},
			confirmPickup(code, index) {
				uni.getLocation({
					type: 'wgs84',
					geocode: true,
					success: (res) => {
						this.$q({
							url: '/rider/takeout/order/confirm-pickup/' + code,
							needToken: true,
							data: {
								location: [res.latitude, res.longitude].join(',')
							}
						}).then(res => {
							uni.showToast({
								title: res.message
							})
							this.originDataList[this.current].splice(index, 1)
						})
					},
					fail: (e) => {
						console.log(e);
					}
				})
			},
			confirmRecevi(code, index) {
				uni.getLocation({
					type: 'wgs84',
					geocode: true,
					success: (res) => {
						this.$q({
							url: '/rider/takeout/order/confirm-arrive/' + code,
							needToken: true,
							data: {
								location: [res.latitude, res.longitude].join(',')
							}
						}).then(res => {
							uni.showToast({
								title: res.message
							})
							this.originDataList[this.current].splice(index, 1)
						})
					},
					fail: (e) => {
						console.log(e);
					}
				})
			},
			navigation(location, shopName) {
				let latLng = location.split(',')
				let url =
					"iosamap://viewMap?sourceApplication=applicationName&poiname={0}&lat={1}&lon={2}&dev=0;".format(shopName,latLng[0], latLng[1])
				plus.runtime.openURL(encodeURI(url), function(e) {
					console.log(e)
					plus.nativeUI.alert("本机未安装指定的地图应用");
				});

				// var packageName = 'com.autonavi.minimap';
				// var main = plus.android.runtimeMainActivity();
				// var packageManager = main.getPackageManager();
				// var PackageManager = plus.android.importClass(packageManager)
				// var packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
				// if (packageInfo) {
				// 	var Uri = plus.android.importClass("android.net.Uri");
				// 	var url = "amapuri://route/plan?sourceApplication=maxuslife" +
				// 		"&sid=A&slat=36.702558&slon=116.876678&sname=起始地点" +
				// 		"&did=B&dlat=36.649415&dlon=117.122497&dname=结束地点&dev=0&t=0";
				// 	var Intent = plus.android.importClass('android.content.Intent');
				// 	var intent = new Intent();
				// 	intent.setAction(Intent.ACTION_VIEW);
				// 	intent.addCategory(Intent.CATEGORY_DEFAULT);
				// 	var uri = Uri.parse(url);
				// 	//将功能Scheme以URI的方式传入data  
				// 	intent.setData(uri);
				// 	intent.setPackage("com.autonavi.minimap");
				// 	var main = plus.android.runtimeMainActivity();
				// 	main.startActivity(intent);
				// } else {
				// 	// alert('未安装' + packageName + '')
				// 	uni.showToast({
				// 		title: `目前导航暂只支持${packageName}`
				// 	})
				// }
			}
		},
		onReachBottom() {
			this.loadMore()
		},
		async onPullDownRefresh() {
			this.pageInfo.pageNum = 1
			this.originDataList[this.current] = []
			await this.loadData()
			uni.stopPullDownRefresh()
		}
	}
</script>

<style lang="scss">
	.header {
		background-color: #262937;
	}

	.card {
		padding: 13px 10px;
		margin-bottom: 8px;
	}

	.task-body-wrap {
		position: relative;

		.time-line-wrap {
			width: 30px;
			height: 100%;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: space-between;
			position: absolute;

			.distance-wrap {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: space-between;
			}
		}

		.addr-content-wrap {
			width: calc(100% - 37px);
			margin-left: 37px;
		}
	}

	.u-steps-item {
		::v-deep .u-steps-item__content .u-text:first-child {
			.u-text__value {
				font-size: 19px !important;
				margin: 3px 0;
			}
		}
	}

	.u-steps-item.active {
		::v-deep .u-steps-item__content .u-text:first-child {
			.u-text__value {
				span {
					font-weight: bolder;
				}
			}
		}
	}
</style>
