package org.xtwy.combine.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;

import java.util.List;

import org.xtwy.netty.codec.MyEncode;

public class CombineDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		if(in.readableBytes()<4){
		  return ;	
		}
		
		int a = in.getUnsignedByte(in.readerIndex());
		int b = in.getUnsignedByte(in.readerIndex()+1);
		
		if(isHttpRes(a,b)){
			ctx.pipeline().addBefore("CombineServerHandler", "http-codec", new HttpServerCodec());
			ctx.pipeline().addBefore("CombineServerHandler", "HttpObjectAggregator", new HttpObjectAggregator(65536));
		}else{//假如是socket
			ctx.pipeline().addBefore("CombineServerHandler","BasedFrameDecoder",new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,Delimiters .lineDelimiter()[0]));
			ctx.pipeline().addBefore("CombineServerHandler","StringDecoder",new StringDecoder());
			ctx.pipeline().addBefore("CombineServerHandler","MyEncode",new MyEncode());
		}
		ctx.pipeline().remove(this);
	}

	private boolean isHttpRes(int a, int b) {
		boolean result = (a=='G' && b=='E' ||
				                     a=='P' && b=='O' ||
				                    a=='P' && b=='U' ||
						            a=='H' && b=='E' ||
						            a=='O' && b=='P' ||
						            a=='P' && b=='A' ||
						             a=='D' && b=='E' ||
								     a=='T' && b=='R' ||
								     a=='C' && b=='O' 
		                        );
		return result;
	}

}
