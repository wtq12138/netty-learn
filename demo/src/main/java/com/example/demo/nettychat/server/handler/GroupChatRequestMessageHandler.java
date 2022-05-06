package com.example.demo.nettychat.server.handler;

import com.example.demo.nettychat.message.GroupChatRequestMessage;
import com.example.demo.nettychat.server.session.GroupSession;
import com.example.demo.nettychat.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        GroupSession groupSession= GroupSessionFactory.getGroupSession();
    }
}
