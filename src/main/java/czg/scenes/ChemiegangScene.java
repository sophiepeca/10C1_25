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
public class ChemiegangScene extends BaseScene{
    public ChemiegangScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/Chemiegang.png")));
    }
}
