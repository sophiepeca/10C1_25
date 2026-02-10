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
public class PhysikgangScene extends BaseScene{
    public PhysikgangScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/Physikgang.png")));
    }
}
