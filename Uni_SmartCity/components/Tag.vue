<template>
	<view class="tag-wrap u-flex" :class="type + ' ' + size" @click="$emit('click')">
		<view class="text">
			<slot name="text">
				{{text}}
			</slot>
		</view>
		<view class="desc" v-if="showDesc">
			<slot name="desc">
				{{desc}}
			</slot>
		</view>
	</view>
</template>

<script>
	
	/**
	 * @event {Function()} click 
	 */
	
	export default {
		name: "Tag",
		props: {
			type: {
				type: String,
				default: 'warning'
			},
			text: {
				type: String,
			},
			desc: {
				type: String,
				default: ""
			},
			bold: {
				type: Boolean,
				default: false
			},
			showDesc: {
				type: Boolean,
				default: true
			},
			size: {
				type: String,
				default: 'normal'
			}
		},
		data() {
			return {

			};
		}
	}
</script>

<style lang="scss">
	.tag-wrap {
		border-radius: 3px;
		overflow: hidden;

		>view {
			box-sizing: border-box;
			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 12px;
		}

		>.desc {
			display: flex;
			align-items: center;
			justify-content: center;
		}

	}


	.tag-wrap.error {
		>view {
			background-color: #f16f51;
		}

		>.text {
			border-top-right-radius: 3px;
			border-bottom-right-radius: 3px;
			color: #fff;
		}

		>.desc {
			border-left: 1px dashed #fff;
			border-top-left-radius: 3px;
			border-bottom-left-radius: 3px;
			color: #fff;
		}
	}

	.tag-wrap.warning {
		border: 1px solid #efe032;
		background-color: #fdfcf1;
		
		>.text{
			color: #eb9645;
		}

		>.desc {
			border-left: 1px solid #efe032;
			color: #f7433d;
		}
	}
	
	.tag-wrap.info {
		border: 1px solid #eee;
		background-color: #ffffff;
	
		>.text{
			color: #000;
		}
	
		>.desc {
			border-left: 1px solid #eee;
			color: #000;
		}
	}

	.tag-wrap.normal {
		>view {
			height: 20px;
			padding: 3px 6px;
		}
	}

	.tag-wrap.larger {
		>view {
			height: 28px;
			padding: 3px 6px;
		}

		>.text {
			min-width: 4em !important;
			padding: 8px 13px;
		}
	}
</style>
