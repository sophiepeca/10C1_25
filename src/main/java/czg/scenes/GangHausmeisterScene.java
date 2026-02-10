/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.util.Images;

/**
 *
 * @author guest-zmpzia
 */
public class GangHausmeisterScene extends BaseScene{
    public GangHausmeisterScene(){
        objects.add(new  BackdropObject(Images.get("/assets/background/GangHausmeister.png")));
    }
}
