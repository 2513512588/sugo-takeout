<template>
	<view class="btn-group">
		<!-- -号 -->
		<view v-if="!readOnly" v-show="quantity > 0" class="minus-btn"
			@click="changeQuantity(quantity - 1)">
			<u-icon name="minus" color="#000" :size="12"></u-icon>
		</view>
	
		<!-- 显示 x Num -->
		<u-icon v-if="readOnly && quantity > 0" name="close" :label="quantity" color="#999"
			labelColor="#999" size="12"></u-icon>
		<span v-else @click="$emit('clickNum', quantity)"
			v-show="quantity > 0">{{quantity}}</span>
	
	
		<!-- 非一份起购并且当前数量小于起购数量 显示 xx份起购 -->
		<view class="plus-btn"
			v-if="!readOnly && min > 1 && quantity < min"
			@click="changeQuantity(quantity + min)">
			<span>{{min}}份起购</span>
		</view>
		<!-- 一份起购或者当前数量大于起购数量显示+号 -->
		<view class="plus-btn"
			v-if="!readOnly && (min === 1 || (min > 1 && quantity >= min))"
			@click="changeQuantity(quantity + 1)">
			<u-icon name="plus" color="#000" :size="12"></u-icon>
		</view>
	</view>
</template>



<script>
	
	/**
	 *  @event {Function(num)} change 
	 *  @event {Function()} clickNum 
	 */
	
	export default {
		name:"Stepper",
		props: {
			quantity: {
				type: Number,
				default: 0
			},
			min: {
				type: Number,
				default: 1
			},
			max: {
				type: Number
			},
			// 模式1 可以修改数量 2为只读
			readOnly: {
				type: Boolean | String,
				default: false
			}
		},
		data() {
			return {
				
			};
		},
		methods: {
			changeQuantity(num){
				let basketNum = Math.max(0, num)
				if (basketNum < this.min) {
					basketNum = 0
				}
				this.$emit('change', parseInt(basketNum))
			}
		}
	}
</script>

<style lang="scss">
	.btn-group {
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 20px;
		margin-right: 15px;
	
		.minus-btn {
			min-width: 20px;
			min-height: 20px;
			border-radius: 5px;
			display: flex;
			align-items: center;
			justify-content: center;
			border: 1px solid #f7df62;
			box-sizing: border-box;
		}
	
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
</style>
