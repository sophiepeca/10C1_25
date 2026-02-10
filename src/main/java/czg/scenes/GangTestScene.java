/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.util.Images;

/**
 *
 * @author user
 */
public class GangTestScene extends BaseScene{
    public GangTestScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/TestGang.png")));
    }
}
