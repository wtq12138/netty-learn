package com.example.demo.nettychat.server.handler;

import com.example.demo.nettychat.message.ChatRequestMessage;
import com.example.demo.nettychat.message.ChatResponseMessage;
import com.example.demo.nettychat.server.session.Session;
import com.example.demo.nettychat.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-06 16:33
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        Session session= SessionFactory.getSession();
        Channel channel = session.getChannel(msg.getTo());
        if(channel==null) {
            ctx.writeAndFlush(new ChatResponseMessage(false,"对方离线"));
        }else {
            channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(),msg.getContent()));
        }
    }
}
