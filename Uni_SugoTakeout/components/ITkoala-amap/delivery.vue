<template>
	<view class="amap-container">
		<view id="amap" :markers="markers" :change:markers="amap.updateEcharts" :style="customStyle"></view>

		<view style="display: none;" id="riderInfoWindow">
			<view class="infoWindow-wrap">
				<view class="infoWindow-content">
					<u-icon name="arrow-right" labelPos="left" :label="getMarker(1).message" size="13px"></u-icon>
					<u-gap height="4px"></u-gap>
					<view class="u-flex">
						<view class="u-flex u-flex-row-start">
							<u-text text="距您" size="13px" color="#999"></u-text>
							<u-text bold :text="getMarker(1).distance + 'km'" size="13px" color="#f7a854"></u-text>
						</view>
						<u-text bold :text="getMarker(1).duration + '分钟'" size="13px" color="#f7a854"></u-text>
					</view>
				</view>
				<view class="sharp">
					<image src="/static/ITkoala-amap/sharp.png" mode="widthFix"></image>
				</view>
			</view>
		</view>

		<view style="display: none;" id="sellerInfoWindow">
			<view class="infoWindow-wrap">
				<view class="infoWindow-content">
					<u-icon name="arrow-right" labelPos="left" :label="getMarker(2).message" size="13px"></u-icon>
				</view>
				<view class="sharp">
					<image src="/static/ITkoala-amap/sharp.png" mode="widthFix"></image>
				</view>
			</view>
		</view>


	</view>
</template>

<script>
	export default {
		props: {
			markers: {
				type: Array,
				default () {
					return [{
						location: {
							lat: 39.90,
							lng: 116.40
						},
						// 1 骑手 2 商家 3 客户
						role: 1,
						status: 1,
						message: '骑手正在赶往商家',
						logo: ''
					}]
				}
			},
			customStyle: {
				type: Object,
				default () {
					return {
						width: '100%',
						height: '100vh',
						transform: 'translateY(calc(-50% + 60px))'
					}
				}
			}
		},
		data() {
			return {}
		},
		mounted() {

		},
		methods: {
			getMarker(role) {
				return this.markers.find(item => item.role === role) || {}
			},
			ready() {
				this.$emit('ready')
			}
		}
	}
</script>

<script module="amap" lang="renderjs">
	import config from './config.js'

	export default {
		data() {
			return {
				map: null,
				markerObjs: []
			}
		},
		mounted() {
			if (typeof window.AMap === 'function') {
				this.initAmap()
			} else {
				// 动态引入较大类库避免影响页面展示
				const script = document.createElement('script')
				script.src = 'https://webapi.amap.com/maps?v=1.4.15&key=' + config.WEBAK
				script.onload = this.initAmap.bind(this)
				document.head.appendChild(script)
			}
		},
		methods: {
			initAmap() {
				this.map = new AMap.Map('amap', {
					resizeEnable: true
				})
				this.map.setZoom(16)
				this.$ownerInstance.callMethod('ready')
			},
			updateEcharts(newValue, oldValue, ownerInstance, instance) {
				let currentShow = newValue.find(item => item.role === 1)
				if (!currentShow) {
					currentShow = newValue.find(item => item.role === 2)
				}
				if (currentShow) {
					// 创建一个 Marker 实例：
					this.map.setCenter([currentShow.location.lng, currentShow.location.lat])

					newValue.forEach((item, index) => {
						let thisMarker = this.markerObjs.find(el => el.role === item.role)
						let position = Object.assign(item.location, {
							Q: item.location.lat,
							R: item.location.lng
						})

						if (thisMarker) {
							this.markerObjs[index].setPosition(position)
							if (item.role !== 3) {
								thisMarker.infoWindow.open(this.map, new AMap.LngLat(position.lng,
									position.lat))
							}
						} else {
							let option = {
								position: position
							}
							if (item.role === 1) {
								option.icon = new AMap.Icon({
									image: item.logo || "./static/ITkoala-amap/delivery/rider.png",
									size: new AMap.Size(42, 42), //图标大小
									imageSize: new AMap.Size(42, 42)
								})
							} else if (item.role === 2) {
								option.icon = new AMap.Icon({
									image: item.logo || './static/ITkoala-amap/delivery/seller.png',
									size: new AMap.Size(32, 32), //图标大小
									imageSize: new AMap.Size(32, 32)
								})
							} else if (item.role === 3) {
								option.icon = new AMap.Icon({
									image: item.logo || "./static/ITkoala-amap/delivery/self.png",
									size: new AMap.Size(32, 32), //图标大小
									imageSize: new AMap.Size(32, 32)
								})
							}
							let marker = new AMap.Marker(option)
							marker.role = item.role
							if (item.role !== 3) {
								marker.infoWindow = this.createInfoWindow(item.role)
								marker.infoWindow.open(this.map, new AMap.LngLat(position.lng, position.lat))
							}
							this.markerObjs.push(marker)
							// 将创建的点标记添加到已有的地图实例：
							this.map.add(marker)
						}
					})

					// this.map.remove()
				}
			},
			createInfoWindow(role) {
				let element = document.getElementById(role === 1 ? 'riderInfoWindow' : 'sellerInfoWindow')
				let infoWindow = new AMap.InfoWindow({
					isCustom: true, //使用自定义窗体
					content: element.children[0],
					offset: new AMap.Pixel(16, -45)
				})
				return infoWindow
			},
		}
	}
</script>

<style lang="scss" scoped>
	.infoWindow-wrap {
		position: relative;
		background: #fff;

		.infoWindow-content {
			padding: 30rpx;

			.infoWindow-text {
				color: #333;
			}

			.close {
				width: 32rpx;
				height: 32rpx;
				position: absolute;
				top: -25rpx;
				right: -15rpx;
			}
		}

		.sharp {
			width: 30rpx;
			height: 23rpx;
			position: absolute;
			bottom: -23rpx;
			left: 0;
			right: 0;
			margin: auto;

			image {
				width: 100%;
				height: 100%;
				vertical-align: top;
			}
		}
	}
</style>
