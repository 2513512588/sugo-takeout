<template>
	<view class="comment_item">
		<u-icon :name="'github-circle-fill'" size="42" ></u-icon>
		<!-- <u-icon :name="item.avatar || 'github-circle-fill'" size="42"></u-icon> -->
		<view class="comment_item_body_wrap">
			<view class="comment_item_user_wrap">
				<p class="comment_user">{{item.userName}}</p>
				<slot></slot>
			</view>
			<slot name="desc"></slot>
			<p class="comment_content">{{item.content}}</p>
			<span class="comment_time">{{item.commentDate}}</span>
		</view>
	</view>
</template>

<script>
	
	// "total": 3,
	// "rows": [{
	// 	"id": 3,
	// 	"userId": 1,
	// 	"activityId": 1,
	// 	"content": "我喜欢这个活动",
	// 	"commentTime": "2021-04-26 16:35:54",
	// 	"activityName": "相约长跑 10 公里",
	// 	"userName": "admin",
	// 	"nickName": "小逻辑",
	// 	"avatar": ""
	// }],
	// "code": 200,
	// "msg": "查询成功"

	export default {
		name: "CommentItem",
		props: {
			item: {
				type: Object
			}
		},
		data() {
			return {

			}
		}
	}
</script>

<style>
	.comment_item {
		width: 100%;
		display: flex;
		align-items: flex-start;
		justify-content: flex-start;
		margin: 15px 0;
		box-sizing: border-box;
		padding: 0 8px;
	}

	.u-avatar {
		width: 40px;
		height: 40px;
	}

	.comment_item_body_wrap {
		width: calc(100% - 40px);
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		justify-content: space-between;
		box-sizing: border-box;
		padding-left: 10px;
	}

	.comment_item_user_wrap {
		width: 100%;
		display: flex;
		align-items: flex-start;
		justify-content: space-between;
	}

	.comment_user {
		font-size: 14px;
		color: #0081de;
		word-break: break-all;
	}

	.comment_content {
		font-size: 14px;
		color: #333;
		word-break: break-all;
	}

	.comment_time {
		font-size: 12px;
		color: #999;
		margin-top: 3px;
	}
</style>
