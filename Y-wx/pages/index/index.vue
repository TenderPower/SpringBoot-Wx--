<template>
	<view class="page">
		<swiper circular="true" duration="1000" interval="5000" class="swiper">
			<swiper-item><image mode="widthFix" src="https://ygkstorage-1305279327.cos.ap-beijing.myqcloud.com/img/banner/swiper-1.jpg"></image></swiper-item>
			<swiper-item><image mode="widthFix" src="https://ygkstorage-1305279327.cos.ap-beijing.myqcloud.com/img/banner/swiper-2.jpg"></image></swiper-item>
			<swiper-item><image mode="widthFix" src="https://ygkstorage-1305279327.cos.ap-beijing.myqcloud.com/img/banner/swiper-3.jpg"></image></swiper-item>
			<swiper-item><image mode="widthFix" src="https://ygkstorage-1305279327.cos.ap-beijing.myqcloud.com/img/banner/swiper-4.jpg"></image></swiper-item>
			<swiper-item><image mode="widthFix" src="https://ygkstorage-1305279327.cos.ap-beijing.myqcloud.com/img/banner/swiper-5.jpg"></image></swiper-item>
		</swiper>
		<!-- 添加跳转消息列表页面 -->
		<view class="notify-container" @tap="toPage('消息提醒', '/pages/message_list/message_list')">
			<view class="notify-title">
				<image src="../../static/icon-1.png" mode="widthFix" class="notify-icon"></image>
				消息提醒
			</view>
			<view class="notify-content">你有{{ unreadRows }}条未读消息</view>
			<image src="../../static/icon-2.png" mode="widthFix" class="more-icon"></image>
		</view>
		<view class="nav-container">
			<view class="nav-row">
				<view class="nav" @tap="toPage('在线签到', '../checkin/checkin')">
					<image mode="widthFix" src="../../static/nav-1.png" class="icon"></image>
					<text class="name">在线签到</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-2.png" mode="widthFix" class="icon"></image>
					<text class="name">员工健康</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-3.png" mode="widthFix" class="icon"></image>
					<text class="name">在线请假</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-4.png" mode="widthFix" class="icon"></image>
					<text class="name">公务出差</text>
				</view>
			</view>
			<view class="nav-row">
				<view class="nav">
					<image src="../../static/nav-5.png" mode="widthFix" class="icon"></image>
					<text class="name">员工日报</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-6.png" mode="widthFix" class="icon"></image>
					<text class="name">我的加班</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-7.png" mode="widthFix" class="icon"></image>
					<text class="name">付款申请</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-8.png" mode="widthFix" class="icon"></image>
					<text class="name">费用报销</text>
				</view>
			</view>
			<view class="nav-row">
				<view class="nav">
					<image src="../../static/nav-9.png" mode="widthFix" class="icon"></image>
					<text class="name">公告通知</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-10.png" mode="widthFix" class="icon"></image>
					<text class="name">在线审批</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-11.png" mode="widthFix" class="icon"></image>
					<text class="name">物品领用</text>
				</view>
				<view class="nav">
					<image src="../../static/nav-12.png" mode="widthFix" class="icon"></image>
					<text class="name">采购申请</text>
				</view>
			</view>
		</view>
		<uni-popup ref="popupMsg" type="top"><uni-popup-message type="success" :message="'接收到' + lastRows + '条消息'" :duration="2000"></uni-popup-message></uni-popup>
	</view>
</template>

<script>
import uniPopup from '@/components/uni-popup/uni-popup.vue';
import uniPopupMessage from '@/components/uni-popup/uni-popup-message.vue';
import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue';
export default {
	// 定义组件
	components: {
		uniPopup,
		uniPopupMessage,
		uniPopupDialog
	},
	data() {
		return {
			timer: null,
			unreadRows: 0,
			lastRows: 0,
			calendar: [],
			meetingPage: 1,
			meetingLength: 20,
			meetingList: [],
			isMeetingLastPage: false
		};
	},
	onLoad: function() {
		let that = this;
		// 建立监听事件
		uni.$on('showMessage', function() {
			that.$refs.popupMsg.open();
		});
		that.ajax(that.url.refreshMessage, 'GET', null, function(resp) {
			that.unreadRows = resp.data.unreadRows;
			that.lastRows = resp.data.lastRows;
			if (that.lastRows > 0) {
				// 触发监听事件
				uni.$emit('showMessage');
			}
		});
	},
	onUnload: function() {
		// 关闭监听对象
		uni.$off('showMessage');
	},
	onShow: function() {
		let that = this;
		that.timer = setInterval(function() {
			that.ajax(that.url.refreshMessage, 'GET', null, function(resp) {
				that.unreadRows = resp.data.unreadRows;
				that.lastRows = resp.data.lastRows;
				if (that.lastRows > 0) {
					uni.$emit('showMessage');
				}
			});
		}, 5000);	
		// // TODO 相关会议
		// that.meetingPage = 1;
		// that.isMeetingLastPage = false;
		// that.meetingList = [];
		// that.loadMeetingList(that);
		// let date = new Date();
		// that.loadMeetingInMonth(that, date.getFullYear(), date.getMonth() + 1);
	},
	onHide: function() {
		let that = this;
		// 隐藏的话，就删除上述的轮回机制
		clearInterval(that.timer);
	},
	methods: {
		toPage: function(name, url) {
			// TODO 验证用户权限

			// 页面跳转
			uni.navigateTo({
				url: url
			});
		},
		// loadMeetingList:function(ref){
		// 	let data={
		// 		page:ref.meetingPage,
		// 		length:ref.meetingLength
		// 	}
		// 	ref.ajax(ref.url.searchMyMeetingListByPage,"POST",data,function(resp){
		// 		let result=resp.data.result
		// 		if(result==null||result.length==0){
		// 			ref.isMeetingLastPage=true
		// 			ref.meetingPage=ref.meetingPage-1
		// 			if(ref.meetingPage>1){
		// 				uni.showToast({
		// 					icon: 'none',
		// 					title: '已经到底了'
		// 				});
		// 			}
		// 		}
		// 		else{
		// 			let list=[]
		// 			for(let one of result){
		// 				for(let meeting of one.list){
		// 					if(meeting.type==1){
		// 						meeting.type="线上会议"
		// 					}
		// 					else if(meeting.type==2){
		// 						meeting.type="线下会议"
		// 					}
		// 					if(meeting.status==3){
		// 						meeting.status = '未开始';
		// 					}
		// 					else if(meeting.status==4){
		// 						meeting.status = '进行中';
		// 					}
		// 					list.push(meeting)
		// 				}
		// 			}
		// 			ref.meetingList=list
		// 		}
		// 	})
		// },
		// loadMeetingInMonth:function(ref,year,month){
		// 	let data={
		// 		year:year,
		// 		month:month
		// 	}
		// 	ref.ajax(ref.url.searchUserMeetingInMonth,"POST",data,function(resp){
		// 		ref.calendar=[]
		// 		for(let one of resp.data.result){
		// 			ref.calendar.push({date:one,info:"会议"})
		// 		}
		// 	})
		// },
		// changeMonth:function(e){
		// 	let that=this
		// 	let year=e.year
		// 	let month=e.month
		// 	that.loadMeetingInMonth(that,year,month)
		// }
	}
};
</script>

<style lang="less">
@import url('index.less');
</style>
