package com.example.demo.nettychat.server.handler;


import com.example.demo.nettychat.message.GroupCreateRequestMessage;
import com.example.demo.nettychat.message.GroupCreateResponseMessage;
import com.example.demo.nettychat.server.session.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;


@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        GroupSession groupSession= GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(msg.getGroupName(), msg.getMembers());
        if(group==null) {
            ctx.writeAndFlush(new GroupCreateResponseMessage(true,"创建成功"));
            List<Channel> channels = groupSession.getMembersChannel(msg.getGroupName());
            for (Channel channel : channels) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true,"欢迎加入"+msg.getGroupName()));
            };
        }else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false,"群已存在"));
        }
    }
}
