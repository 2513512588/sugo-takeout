<template>
	<view class="amap-container">
		<view id="amap" :currentPosition="currentPosition" :change:currentPosition="amap.updateEcharts"
			:style="customStyle"></view>

		<view style="display: none;" id="infoWindow">
			<view class="infoWindow-wrap">
				<view class="infoWindow-content">
					<text class="infoWindow-text">当前点击的对象的index值为：</text>
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
			currentPosition: {
				type: Object,
				default () {
					return {
						lat: 39.90,
						lng: 116.40
					}
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
				marker: null
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
				this.$ownerInstance.callMethod('ready')
			},
			updateEcharts(newValue, oldValue, ownerInstance, instance) {

				// 创建一个 Marker 实例：
				this.map.setCenter([newValue.lng, newValue.lat])
				this.map.setZoom(16)

				let option = {
					position: Object.assign(newValue, {
						Q: newValue.lat,
						R: newValue.lng
					})
				}
				if (this.marker) {
					this.marker.setPosition(option.position);
				} else {
					let thisMarker = new AMap.Marker(option)
					this.marker = thisMarker
					// 将创建的点标记添加到已有的地图实例：
					this.map.add(thisMarker)
				}

				// this.markers.forEach(item =>{
				// 	this.map.remove(item)
				// })
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
