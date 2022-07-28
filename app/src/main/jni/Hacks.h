#ifndef HACKS_H
#define HACKS_H

Request request;
Response response;
char extra[100];
char extraa[100];
char status[100];
int playerCount;
float x,y,w,h,top,right,left,bottom,x1,top1,top2,setting;
Color bonecolour,boxcolour;

void DrawESP(ESP esp,int width,int height)
{
	send((void *)&request, sizeof(request));
	receive((void *)&response);
	playerCount = 0;
    esp.drawfunction();
	if (response.Success)
	{
        
		if (isPlayerRadar){
			esp.DrawPlayerRadar();//画雷达
		}
       
		for (int i = 0; i < response.PlayerCount; i++)
		{
			x=response.Players[i].x;
			y=response.Players[i].y;
			w=response.Players[i].w;
            h=response.Players[i].h;
            
			playerCount++;
           			//设置每个队的颜色
			esp.DrawPlayerIng(response.Players[i].TeamID);
			
			if(isPlayerRadar){
                if(x < 3100 && w > -90 && x > -890 && w && 0 <= x && 0 <= w && response.Players[i].Distance < 600 && 0 < response.Players[i].Distance && 0 < h){
			esp.DrawPlayerRadarXY(response.Players[i].isBot,response.Players[i].x,response.Players[i].y);
			}
           }
		if (response.Players[i].isBot==0) {
			bonecolour.colour=0xffff0000;
			boxcolour.colour=0xffff0000;
                } else {
			bonecolour.colour=0xff00ff00;
			boxcolour.colour=0xff00ff00;
           }
		   
		   
		         x1 = x + w / 2 + setting;
                 top1 = response.Players[i].Pelvis.y - response.Players[i].Head.y;
                  top = y-w;
                  top2 =y-(h/2);
                  bottom = y+w;
                  left = x1-(w/2);
                  right = x1 +(w /2);
				  //显示射线
		   	if (w>0&&isPlayerLine){
			esp.DrawPlayerLine(width/2+setting,123,x1,top);
			}
			if(w>0&&x+w>0&&x<width&&y+w>0&&y<height){
				//显示方框
			if (isPlayerBox){
			esp.DrawPlayerBox(left,top,right,bottom);
			}
			if(isPlayerDist)
			{
				sprintf(extra, "[%.0f M]", response.Players[i].Distance);
				esp.DrawPlayerDistance(extra,x1,bottom+20);
			}
			//显示血量
			if(isPlayerHealth){
			esp.DrawPlayerHealth(response.Players[i].Health,x1,top,w);
			}
			//显示昵称
			if(isPlayerName){
			if (response.Players[i].isBot==0)
			{
			sprintf(extra, "%s", response.Players[i].PlayerName);
            sprintf(extraa, "%d", response.Players[i].TeamID);
            esp.DrawPlayerName(extra,x1,top-36);
			esp.DrawPlayerName(extraa,x1-101,top-36);
			}
			else{
                sprintf(extraa, "%d", response.Players[i].TeamID);
                esp.Drawpictures(2,x1-121,top-62);
				esp.DrawPlayerName("ROBOT",x1,top-32);
               // esp.DrawPlayerName(extraa,x1-101,top-36);
				/*esp.DrawPlayerName("[AI]",x1+17.5f,top-32);
				esp.Draw_ic_Bot(x1,top);*/
			}
		}
			//显示骨骼
           
			if(isPlayerBone){
                            esp.DrawCircle(bonecolour,response.Players[i].Head.x+setting,response.Players[i].Head.y,w/9);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Head.x+setting,response.Players[i].Head.y,response.Players[i].Chest.x+setting,response.Players[i].Chest.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Chest.x+setting,response.Players[i].Chest.y,response.Players[i].Pelvis.x+setting,response.Players[i].Pelvis.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Head.x+setting,response.Players[i].Head.y,response.Players[i].Left_Shoulder.x+setting,response.Players[i].Left_Shoulder.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Head.x+setting,response.Players[i].Head.y,response.Players[i].Right_Shoulder.x+setting,response.Players[i].Right_Shoulder.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Left_Shoulder.x+setting,response.Players[i].Left_Shoulder.y,response.Players[i].Left_Elbow.x+setting,response.Players[i].Left_Elbow.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Right_Shoulder.x+setting,response.Players[i].Right_Shoulder.y,response.Players[i].Right_Elbow.x+setting,response.Players[i].Right_Elbow.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Left_Elbow.x+setting,response.Players[i].Left_Elbow.y,response.Players[i].Left_Wrist.x+setting,response.Players[i].Left_Wrist.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Right_Elbow.x+setting,response.Players[i].Right_Elbow.y,response.Players[i].Right_Wrist.x+setting,response.Players[i].Right_Wrist.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Pelvis.x+setting,response.Players[i].Pelvis.y,response.Players[i].Left_Thigh.x+setting,response.Players[i].Left_Thigh.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Pelvis.x+setting,response.Players[i].Pelvis.y,response.Players[i].Right_Thigh.x+setting,response.Players[i].Right_Thigh.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Left_Thigh.x+setting,response.Players[i].Left_Thigh.y,response.Players[i].Left_Knee.x+setting,response.Players[i].Left_Knee.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Right_Thigh.x+setting,response.Players[i].Right_Thigh.y,response.Players[i].Right_Knee.x+setting,response.Players[i].Right_Knee.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Left_Knee.x+setting,response.Players[i].Left_Knee.y,response.Players[i].Left_Ankle.x+setting,response.Players[i].Left_Ankle.y);
                            esp.DrawPlayerBone(bonecolour,response.Players[i].Right_Knee.x+setting,response.Players[i].Right_Knee.y,response.Players[i].Right_Ankle.x+setting,response.Players[i].Right_Ankle.y);
				}
			}
			if(isr360Alert){
				sprintf(extra, "%.0f M", response.Players[i].Distance);
				if(x+w/2<0){//左侧
					esp.DrawPlayer360(0+setting,y-25,90+setting,y+25);
					esp.DrawPlayer360Text(extra,45+setting,y+8);
				}else if(w>0&&x>width){//右侧
					esp.DrawPlayer360(width+setting,y-25,width-90+setting,y+25);
					esp.DrawPlayer360Text(extra,width-45+setting,y+8);
				}else if(w>0&&y+w<0){//上方
					esp.DrawPlayer360(x-45+setting,0,x+45+setting,50);
					esp.DrawPlayer360Text(extra,x+setting,35);
				}/*else if(w<0){//下方
					esp.DrawPlayer360Alert(x,height,60);
					esp.DrawPlayer360Text(extra,x+10,height-20);
				}*/
			}
		}
		}
	sprintf(extra, "%d", playerCount);
  //  sprintf(fww, "%f", aimfw);
  if(playerCount == 0) {
    esp.Drawpictures(1,width/2-100+setting,68);  
	esp.DrawText("CLEAR",width/2+setting,100);
   }
   else if(playerCount >0){
    esp.Drawpictures(0,width/2-100+setting,68);  
	esp.DrawText(extra,width/2+setting,100);  
       
   }
      if (isAimcircle){
       
    esp.DrawCircle(0xffff0000,width/2+setting,height/2,request.fw);
     }
}

#endif //HACKS_H
