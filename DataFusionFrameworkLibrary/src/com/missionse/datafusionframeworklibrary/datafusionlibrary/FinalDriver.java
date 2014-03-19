/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.missionse.datafusionframeworklibrary.datafusionlibrary;

import com.missionse.datafusionframeworklibrary.dataassociationlibrary.DataAssociation;
import com.missionse.datafusionframeworklibrary.databaselibrary.Source;

/**
 *
 * @author Team3
 */
public class FinalDriver {

//    static PacketReceiver packetReceiver = new PacketReceiver();
    // First source will be sent to packetReceiver   
    static Source source1;
    // Second source will be sent to packetReceiver
    static Source source2;
    static DataAssociation da  = new DataAssociation();


    public static void main(String[] args) throws InterruptedException {

        FinalDriver fd = new FinalDriver();

    }

    public FinalDriver() throws InterruptedException {

        source1 = new Source("1", "platform", "category", 1, .3, 0.3, 99,
                99, 99, 0.4, .1, .1, 40, 41.99417, -119.305344, .1, 2500, 0.2);

        source2 = new Source("2", "platform", "category", 1, 0.3, 0.2, 99,
                99, 99, 0.3, .2, .1, 40, 41.99422, -119.305333, .1, 2500, 0.2);

        //source1.setPositionLatitude(0.0);
        //source1.setPositionLongitude(0.0);




        //99's == source
        //.1 == errors
        //40 = update hertz


//        source1 = new DriverSource("1", 41.94417, -119.305344, 2500.00);
//        source2 = new DriverSource("2", 41.94424, -119.305366, 2500.00);

//        packetReceiver.recievePacket(source1.toString());
//        packetReceiver.recievePacket(source2.toString());
        da.associateMeasurement(source1.toString());

        int counter = 0;
        
        while (true) {

            
            if (counter < 2) {
                source1.setPositionLatitude(source1.getPositionLatitude() + .5);
                source1.setPositionLongitude(source1.getPositionLongitude() + .5);

                source2.setPositionLatitude(source2.getPositionLatitude() + .6);
                source2.setPositionLongitude(source2.getPositionLongitude() + .6);

//                packetReceiver.recievePacket(source1.toString());
//                packetReceiver.recievePacket(source2.toString());

                counter++;

                Thread.sleep(100);
            } /**else {
                counter = 0;


                source1 = new Source("1", "platform", "category", 1, 0.0, 0.0, 99,
                        99, 99, 0.0, .1, .1, 40, 41.99417, -119.305344, .1, 2500, 0.0);

                source2 = new Source("1", "platform", "category", 1, 0.0, 0.0, 99,
                        99, 99, 0.0, .1, .1, 40, 41.99422, -119.305333, .1, 2500, 0.0);


            }
*/
            

        }



    }
}
