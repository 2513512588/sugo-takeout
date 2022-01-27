<template>
	<view class="amap-container">
		<view :currentPosition="currentPosition" :change:currentPosition="amap.updateEcharts" id="amap"></view>
	</view>
</template>

<script>
	export default {
		props: {
			currentPosition: {
				type: Object,
				default () {
					return {

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
			},
			updateEcharts(newValue, oldValue, ownerInstance, instance) {
				// 创建一个 Marker 实例：
				this.map.setCenter([newValue.lng, newValue.lat])
				this.map.setZoom(17)

				let option = {
					position: Object.assign(newValue, {
						Q: newValue.lat,
						R: newValue.lng
					}),
				}
				if (this.marker) {
					this.marker.setPosition(option.position);
				} else {
					this.marker = new AMap.Marker(option)
					// 将创建的点标记添加到已有的地图实例：
					this.map.add(this.marker)
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	#amap {
		width: 100%;
		height: 100vh;
		transform: translateY(calc(-50% + 60px));
	}
</style>
