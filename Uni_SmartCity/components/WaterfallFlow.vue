<template>
	<view class="flow_wrap">
		<view class="flow_col" v-for="(list,index) in dataList" :key="index">

			<view class="card" v-for="(item, idx) in list" :key="idx" @click="goDetail(item[primaryKeyName])">
				<view v-if="coverKeyName">
					<image style="width: 100%;" @load="loadComplete(idx, $event)" @error="loadComplete(idx, $event)"
						mode="widthFix" :src="item[coverKeyName]"></image>
					<view class="card_body">
						<slot name="item" :item="item"></slot>
					</view>
				</view>
				<slot v-else name="item" :item="item"></slot>
			</view>


			<view class="card seize" v-show="loading === index">
				<u-gap height="5px"></u-gap>
				<view class="wrap">
					<u-skeleton :rows="5" :loading="true"></u-skeleton>
				</view>
				<u-gap height="5px"></u-gap>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		name: "WaterfallFlow",
		props: {
			list: {
				type: Array
			},
			coverKeyName: {
				type: String
			},
			//左侧最大距离
			leftDistance: {
				type: Number,
				default: 25
			},
			primaryKeyName: {
				type: String,
				default: 'id'
			}
		},
		data() {
			return {
				dataList: [
					[],
					[]
				],
				current: 0,
				//加载状态 0加载左边 1加载右边 3不加载
				loading: 0,
				leftHeight: 0,
				rightHeight: 0,
			}
		},
		methods: {
			loadComplete(idx, e) {
				const query = uni.createSelectorQuery().in(this);
				query.selectAll('.flow_col').boundingClientRect(data => {
					data.forEach((el, index) => {
						if (index === 0) {
							this.leftHeight = el.height
						} else {
							this.rightHeight = el.height
						}
					})
					this.current = this.dataList[0].length + this.dataList[1].length
					if (this.current < this.list.length) {
						if (this.leftHeight - this.rightHeight > this.leftDistance) {
							//加载右边
							this.dataList[1].push(this.list[this.current])
							this.loading = 0
						} else {
							this.dataList[0].push(this.list[this.current])
							//加载左边
							this.loading = 1
						}
						this.$forceUpdate()
					} else {
						this.loading = 3
					}
				}).exec()
			},
			goDetail(id){
				this.$emit('clickItem', id)
			}
		},
		mounted() {
			this.loading = 1
			this.dataList[0].push(this.list[this.current])
			this.$forceUpdate()
		}
	}
</script>

<style lang="scss">
	.flow_wrap {
		width: 100%;
		display: flex;
		align-items: flex-start;
		justify-content: space-between;
		padding: 5px 10px;
		box-sizing: border-box;
	}

	.flow_wrap .flow_col {
		width: calc(50% - 5px);
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-between;
	}

	.card {
		width: 100% !important;
		margin: 5px 0 !important;
	}

	.card:not(.seize) {
		padding: 0 !important;
	}

	.card.seize {
		margin-top: 10px !important;
	}

	.card:not(.seize) .card_body {
		padding-top: 0 !important;
		padding-bottom: 5px;
	}
</style>
