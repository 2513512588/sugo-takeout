<template>
	<view class="goods-item" :class="'size-' + size">
		<image class="goods-cover" :src="item.cover" mode="widthFix"></image>
		<view class="goods-info-wrap">
			<u-text :lines="2" :text="item.name" size="15px" color="#333" bold></u-text>
			<u-text :lines="2" :text="item.description" size="12" color="#999"></u-text>
			<view class="goods-info-detail">
				<span v-if="item.salesVolume || item.salesVolume === 0">月销{{item.salesVolume}}</span>
				<span v-if="item.score || item.score === 0">好评率{{item.score}}%</span>
			</view>
			<view class="goods-price-wrap">
				<u-icon name="rmb" :label="item.price" color="#eb7118" labelColor="#eb7118" bold></u-icon>

				<!-- 步进器 -->
				<Stepper v-if="item.skus.length === 0" :readOnly="readOnly" :max="item.maxPurchaseNum"
					:min="item.minPurchaseNum" :quantity="item.basketNum" @change="changeQuantity"
					@clickNum="$emit('clickBasketNum', item.id, $event)" />

				<view class="btn-group" v-else>
					<view class="plus-btn" @click="$emit('clickSku', item.id)">
						<span>选规格</span>
						<u-badge type="error" max="99" :value="item.basketNum" absolute :offset="[0, 0]"
							style="transform: translate(50%, -50%);"></u-badge>
					</view>
				</view>

			</view>
		</view>


	</view>
</template>

<script>
	import Stepper from '@/components/Stepper.vue'
	/**
	 * goodsItem 标签
	 * @description goodsItem 组件用于解析单个商品和修改购物车数量
	 * @property {Object} item	商品数据
	 * @property {Boolean | String}	readOnly 是否可以修改购物车数量
	 * @property {String} size 组件大小
	 * @event {Function(index)} change 加购数量发生变化时触发
	 * @event {Function(index)} clickBasketNum 点击加购数量时触发
	 * @event {Function(index)} clickSku 点击选规格时触发
	 * @example <goodsItem v-for="(item,index) in goodsList" :item="item" :key="item.id" 
								@clickBasketNum="onClickBasketNum"
								@clickSku="onClickSku"
								@change="onUpdateQuantity" />
	 */
	export default {
		name: "goodsItem",
		props: {
			item: {
				type: Object
			},
			// 模式1 可以修改数量 2为只读
			readOnly: {
				type: Boolean | String,
				default: false
			},
			size: {
				type: String,
				default: 'normal'
			}
		},
		components: {
			Stepper
		},
		data() {
			return {

			}
		},
		methods: {
			changeQuantity(quantity) {
				this.$emit('change', this.item.id, quantity, this.item.skuIdGroup)
			},
		},
		// watch: {
		// 	item: {
		// 		handler(newVal, oldVal) {
		// 			this.basketNum = newVal.basketNum || 0
		// 		},
		// 		deep: true,
		// 		//初始化也会触发
		// 		immediate: true
		// 	}
		// }
	}
</script>

<style lang="scss">
	.goods-item.size-mini {
		.goods-cover {
			width: 25% !important;
		}

		.goods-info-wrap {
			width: 75%;
		}
	}

	.goods-item {
		width: 100%;
		display: flex;
		align-items: flex-start;
		justify-content: space-between;
		box-sizing: border-box;
		padding: 6px;
		margin: 5px 0;

		.goods-cover {
			border-radius: 5px;
			width: 35%;
			// border: 1px solid red;
		}

		.goods-info-wrap {
			width: 65%;
			padding-left: 10px;
			box-sizing: border-box;
			overflow: hidden auto;

			.goods-info-detail {
				display: flex;
				align-items: center;
				justify-content: flex-start;
				font-size: 12px;
				color: #999;
				margin: 0 0 3px;

				span {
					margin-right: 6px;
				}
			}

			.goods-price-wrap {
				width: 100%;
				display: flex;
				align-items: flex-start;
				justify-content: space-between;

				.btn-group {
					display: flex;
					align-items: center;
					justify-content: space-between;
					height: 20px;
					margin-right: 15px;

					span {
						margin: 0 6px;
						text-align: center;
						font-size: 13px;
					}

					.plus-btn {
						background-color: #f7df62;
						min-width: 20px;
						min-height: 20px;
						border-radius: 5px;
						display: flex;
						align-items: center;
						justify-content: center;
						box-sizing: border-box;
						position: relative;
					}
				}
			}
		}

	}
</style>
