package org.vedibarta.app;

import android.util.SparseArray;

import org.vedibarta.app.model.Par;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e560 on 11/05/17.
 */

public class ParashotHelper {

    public static final String BASE_URL_ZIP = "http://www.vedibarta.org/Rashi_Tora_ZIP/";
    public static final String BASE_URL_MP3 = "http://www.vedibarta.org/Rashi_Tora_MP3/";

    static String dedication0 = "עדיין לא הוקדש", dedication5;
    public static List<Par> parList = new ArrayList<>();
    private static String dedication1, dedication2, dedication3, dedication4, dedication6, dedication7, dedication8, dedication9;

    static {

        //index 0
        parList.add( new Par(
                "בראשית",
                "35.0",
                dedication1,
                "1-ber/01-bereshit/bereshit1.zip,1-ber/01-bereshit/bereshit2.zip",
                "bereshit", new String[][]{
                {"bereshit.1a.mp3","3.0mb","13:13"},
                {"bereshit.1b.mp3","1.8mb","07:58"},
                {"bereshit.1c.mp3","1.0mb","04:26"},
                {"bereshit.2a.mp3","2.2mb","09:28"},
                {"bereshit.2b.mp3","1.9mb","08:07"},
                {"bereshit.3a.mp3","2.7mb","11:35"},
                {"bereshit.3b.mp3","2.1mb","09:01"},
                {"bereshit.3c.mp3","2.2mb","09:44"},
                {"bereshit.3d.mp3","1.9mb","08:15"},
                {"bereshit.4a.mp3","2.7mb","11:40"},
                {"bereshit.4b.mp3","2.7mb","11:44"},
                {"bereshit.5a.mp3","2.0mb","08:54"},
                {"bereshit.5b.mp3","2.2mb","09:30"},
                {"bereshit.5c.mp3","2.1mb","09:09"},
                {"bereshit.6.mp3","2.2mb","09:38"},
                {"bereshit.7.mp3","2.4mb","10:29"}
        }));
        //index 1
        parList.add( new Par(
                "נח",
                "29.7",
                dedication2,
                "1-ber/02-noah/noah1.zip,1-ber/02-noah/noah2.zip",
                "noah", new String[][]{
                {"noah.01a.mp3","2.6mb","11:30"},
                {"noah.01b.mp3","2.4mb","10:34"},
                {"noah.02a.mp3","2.0mb","08:52"},
                {"noah.02b.mp3","1.5mb","06:38"},
                {"noah.03.mp3","1.6mb","07:03"},
                {"noah.04a.mp3","2.3mb","09:58"},
                {"noah.04b.mp3","1.9mb","08:10"},
                {"noah.05.mp3","1.8mb","07:58"},
                {"noah.06.mp3","2.0mb","08:33"},
                {"noah.07.mp3","1.3mb","05:33"},
                {"noah.08.mp3","2.8mb","12:24"},
                {"noah.09.mp3","2.7mb","11:51"},
                {"noah.10.mp3","2.7mb","11:43"},
                {"noah.11.mp3","2.1mb","08:57"}
        }));

        //index 2
        parList.add( new Par(
                "לך לך",
                "31.5",
                dedication0,
                "1-ber/03-lechlecha/lechlecha1.zip,1-ber/03-lechlecha/lechlecha2.zip",
                "lechlecha", new String[][]{
                {"lechlecha.01a.mp3","2.4mb","10:23"},
                {"lechlecha.01b.mp3","2.4mb","10:27"},
                {"lechlecha.02.mp3","2.8mb","12:08"},
                {"lechlecha.03a.mp3","1.8mb","08:02"},
                {"lechlecha.03b.mp3","1.9mb","08:10"},
                {"lechlecha.04a.mp3","2.7mb","11:40"},
                {"lechlecha.04b.mp3","2.8mb","12:08"},
                {"lechlecha.05a.mp3","1.6mb","07:08"},
                {"lechlecha.05b.mp3","2.2mb","09:49"},
                {"lechlecha.05c.mp3","2.0mb","08:40"},
                {"lechlecha.06.mp3","2.7mb","11:56"},
                {"lechlecha.07.mp3","2.1mb","09:18"},
                {"lechlecha.08.mp3","0.6mb","02:42"},
                {"lechlecha.09a.mp3","1.7mb","07:25"},
                {"lechlecha.09b.mp3","1.8mb","07:38"}
        }));

        //index 3
        parList.add( new Par(
                "וירא",
                "31.0",
                dedication0,
                "1-ber/04-vayera/vayera1.zip,1-ber/04-vayera/vayera2.zip",
                "vayera", new String[][]{
                {"vayera.01a.mp3","3.0mb","13:03"},
                {"vayera.01b.mp3","2.4mb","10:20"},
                {"vayera.02.mp3","2.4mb","10:29"},
                {"vayera.03a.mp3","1.9mb","08:30"},
                {"vayera.03b.mp3","2.1mb","09:16"},
                {"vayera.04a.mp3","2.6mb","11:20"},
                {"vayera.04b.mp3","2.7mb","11:55"},
                {"vayera.05.mp3","1.3mb","05:42"},
                {"vayera.06.mp3","1.0mb","04:31"},
                {"vayera.07a.mp3","1.8mb","07:49"},
                {"vayera.07b.mp3","1.7mb","07:22"},
                {"vayera.08.mp3","1.2mb","05:17"},
                {"vayera.09.mp3","2.6mb","11:29"},
                {"vayera.10a.mp3","2.1mb","09:05"},
                {"vayera.10b.mp3","2.0mb","08:53"}
        }));

        //index 4
        parList.add( new Par(
                "חיי שרה",
                "19.1",
                dedication0,
                "1-ber/05-hayeysara/hayeysara1.zip,1-ber/05-hayeysara/hayeysara2.zip",
                "hayeysara", new String[][]{
                {"hayeysara.1a.mp3","2.2mb","09:28"},
                {"hayeysara.1b.mp3","2.3mb","10:04"},
                {"hayeysara.2.mp3","1.5mb","06:44"},
                {"hayeysara.3a.mp3","2.3mb","10:00"},
                {"hayeysara.3b.mp3","1.6mb","06:52"},
                {"hayeysara.4.mp3","3.2mb","13:49"},
                {"hayeysara.5.mp3","2.6mb","11:21"},
                {"hayeysara.6.mp3","1.7mb","07:19"},
                {"hayeysara.7.mp3","1.7mb","07:34"}
        }));

        //index 5
        parList.add( new Par(
                "תולדות",
                "20.3",
                dedication0,
                "1-ber/06-toldot/toldot1.zip,1-ber/06-toldot/toldot2.zip",
                "toldot", new String[][]{
                {"toldot.1a.mp3","1.7mb","07:27"},
                {"toldot.1b.mp3","2.1mb","09:12"},
                {"toldot.2.mp3","2.3mb","09:57"},
                {"toldot.3.mp3","2.1mb","09:01"},
                {"toldot.4a.mp3","1.9mb","08:19"},
                {"toldot.4b.mp3","1.2mb","05:12"},
                {"toldot.5a.mp3","2.1mb","09:00"},
                {"toldot.5b.mp3","2.3mb","10:01"},
                {"toldot.6.mp3","3.0mb","13:09"},
                {"toldot.7.mp3","1.6mb","07:08"}
        }));

        //index 6
        parList.add( new Par(
                "ויצא",
                "23.6",
                dedication0,
                "1-ber/07-vayetze/vayetze1.zip,1-ber/07-vayetze/vayetze2.zip",
                "vayetze", new String[][]{
                {"vayetze.1a.mp3","2.6mb","11:11"},
                {"vayetze.1b.mp3","2.5mb","10:58"},
                {"vayetze.2.mp3","1.9mb","08:15"},
                {"vayetze.3.mp3","2.7mb","11:45"},
                {"vayetze.4a.mp3","2.1mb","09:10"},
                {"vayetze.4b.mp3","2.1mb","09:21"},
                {"vayetze.5.mp3","2.9mb","12:44"},
                {"vayetze.6.mp3","1.8mb","08:04"},
                {"vayetze.7.mp3","2.8mb","12:16"},
                {"vayetze.8.mp3","2.1mb","09:15"}
        }));

        //index 7
        parList.add( new Par(
                "וישלח",
                "28.2",
                dedication0,
                "1-ber/08-vayishlach/vayishlach1.zip,1-ber/08-vayishlach/vayishlach2.zip",
                "vayishlach", new String[][]{
                {"vayishlach.1a.mp3","2.1mb","09:09"},
                {"vayishlach.1b.mp3","2.4mb","10:37"},
                {"vayishlach.2.mp3","3.0mb","12:53"},
                {"vayishlach.3.mp3","2.1mb","09:01"},
                {"vayishlach.4a.mp3","2.5mb","10:43"},
                {"vayishlach.4b.mp3","2.5mb","10:46"},
                {"vayishlach.5.mp3","2.5mb","11:05"},
                {"vayishlach.6a.mp3","2.3mb","10:02"},
                {"vayishlach.6b.mp3","2.1mb","09:18"},
                {"vayishlach.7.mp3","2.2mb","09:43"},
                {"vayishlach.8a.mp3","2.2mb","09:28"},
                {"vayishlach.8b.mp3","2.3mb","10:06"}
        }));

        //index 8
        parList.add( new Par(
                "וישב",
                "22.5",
                dedication0,
                "1-ber/09-vayeshev/vayeshev1.zip,1-ber/09-vayeshev/vayeshev2.zip",
                "vayeshev", new String[][]{
                {"vayeshev.1a.mp3","3.1mb","13:22"},
                {"vayeshev.1b.mp3","1.6mb","07:07"},
                {"vayeshev.2.mp3","2.5mb","10:48"},
                {"vayeshev.3a.mp3","2.1mb","09:11"},
                {"vayeshev.3b.mp3","2.0mb","08:30"},
                {"vayeshev.4a.mp3","2.7mb","11:48"},
                {"vayeshev.4b.mp3","2.6mb","11:15"},
                {"vayeshev.5.mp3","2.7mb","11:54"},
                {"vayeshev.6a.mp3","1.8mb","07:41"},
                {"vayeshev.6b.mp3","1.5mb","06:40"}
        }));

        //index 9
        parList.add( new Par(
                "מקץ",
                "27.6",
                dedication0,
                "1-ber/10-miketz/miketz1.zip,1-ber/10-miketz/miketz2.zip",
                "miketz", new String[][]{
                {"miketz.1a.mp3","2.2mb","09:31"},
                {"miketz.1b.mp3","1.7mb","07:22"},
                {"miketz.2.mp3","2.8mb","12:16"},
                {"miketz.3a.mp3","2.2mb","09:32"},
                {"miketz.3b.mp3","2.7mb","11:47"},
                {"miketz.4a.mp3","2.3mb","09:53"},
                {"miketz.4b.mp3","1.8mb","08:04"},
                {"miketz.5a.mp3","1.6mb","07:06"},
                {"miketz.5b.mp3","1.5mb","06:31"},
                {"miketz.6.mp3","2.8mb","12:06"},
                {"miketz.7a.mp3","1.8mb","08:00"},
                {"miketz.7b.mp3","1.8mb","07:44"},
                {"miketz.8.mp3","2.4mb","10:36"}
        }));

        //index 10
        parList.add( new Par(
                "ויגש",
                "16.0",
                dedication0,
                "1-ber/11-vayigash/vayigash.zip",
                "vayigash", new String[][]{
                {"vayigash.1.mp3","2.5mb","10:51"},
                {"vayigash.2.mp3","2.6mb","11:18"},
                {"vayigash.3.mp3","1.5mb","06:41"},
                {"vayigash.4.mp3","1.2mb","05:06"},
                {"vayigash.5.mp3","2.6mb","11:14"},
                {"vayigash.6.mp3","1.7mb","07:14"},
                {"vayigash.7.mp3","1.4mb","06:01"},
                {"vayigash.8.mp3","2.6mb","11:23"}
        }));


        //index 11
        parList.add( new Par(
                "ויחי",
                "27.7",
                dedication0,
                "1-ber/12-vayehi/vayehi1.zip,1-ber/12-vayehi/vayehi2.zip",
                "vayehi", new String[][]{
                {"vayehi.1.mp3","2.5mb","11:06"},
                {"vayehi.2a.mp3","2.1mb","09:07"},
                {"vayehi.2b.mp3","2.0mb","08:41"},
                {"vayehi.3a.mp3","1.4mb","06:14"},
                {"vayehi.3b.mp3","1.6mb","06:47"},
                {"vayehi.4a.mp3","1.3mb","05:35"},
                {"vayehi.4b.mp3","1.9mb","08:14"},
                {"vayehi.5a.mp3","1.8mb","08:00"},
                {"vayehi.5b.mp3","2.4mb","10:18"},
                {"vayehi.6.mp3","2.5mb","10:42"},
                {"vayehi.7a.mp3","2.3mb","09:51"},
                {"vayehi.7b.mp3","1.7mb","07:21"},
                {"vayehi.8.mp3","1.1mb","04:39"},
                {"vayehi.9a.mp3","1.9mb","08:22"},
                {"vayehi.9b.mp3","1.4mb","06:00"}
        }));

        //index 12
        parList.add( new Par(
                "שמות",
                "26.5",
                dedication1,
                "2-shm/01-shemot/shemot1.zip,2-shm/01-shemot/shemot2.zip",
                "shemot", new String[][]{
                {"shemot.1a.mp3","1.9mb","08:06"},
                {"shemot.1b.mp3","1.4mb","06:13"},
                {"shemot.2a.mp3","1.8mb","07:53"},
                {"shemot.2b.mp3","1.6mb","07:01"},
                {"shemot.3a.mp3","2.1mb","09:08"},
                {"shemot.3b.mp3","1.3mb","05:28"},
                {"shemot.4a.mp3","1.9mb","08:17"},
                {"shemot.4b.mp3","1.4mb","05:59"},
                {"shemot.5a.mp3","2.4mb","10:32"},
                {"shemot.5b.mp3","1.7mb","07:33"},
                {"shemot.6.mp3","2.8mb","12:10"},
                {"shemot.7.mp3","2.4mb","10:18"},
                {"shemot.8a.mp3","2.3mb","09:58"},
                {"shemot.8b.mp3","1.6mb","07:07"}
        }));

        parList.add( new Par(
                "וארא",
                "21.7",
                dedication0,
                "2-shm/02-vaera/vaera1.zip,2-shm/02-vaera/vaera2.zip",
                "vaera", new String[][]{
                {"vaera.1a.mp3","2.2mb","09:42"},
                {"vaera.1b.mp3","2.4mb","10:41"},
                {"vaera.2.mp3","2.4mb","10:41"},
                {"vaera.3.mp3","1.9mb","08:06"},
                {"vaera.4a.mp3","2.1mb","09:04"},
                {"vaera.4b.mp3","2.1mb","09:00"},
                {"vaera.5.mp3","3.0mb","13:10"},
                {"vaera.6.mp3","1.6mb","07:09"},
                {"vaera.7.mp3","3.9mb","16:56"}
        }));

        parList.add( new Par(
                "בא",
                "25.5",
                dedication1,
                "2-shm/03-bo/bo1.zip,2-shm/03-bo/bo2.zip",
                "bo", new String[][]{
                {"bo.01a.mp3","1.7mb","07:34"},
                {"bo.01b.mp3","1.8mb","07:52"},
                {"bo.03.mp3","2.0mb","08:31"},
                {"bo.04a.mp3","1.4mb","06:09"},
                {"bo.04b.mp3","1.6mb","06:52"},
                {"bo.05a.mp3","2.2mb","09:26"},
                {"bo.05b.mp3","1.7mb","07:19"},
                {"bo.05c.mp3","1.5mb","06:44"},
                {"bo.06.mp3","2.6mb","11:19"},
                {"bo.07.mp3","1.2mb","05:23"},
                {"bo.08a.mp3","1.7mb","07:12"},
                {"bo.08b.mp3","1.5mb","06:27"},
                {"bo.09.mp3","1.1mb","04:51"},
                {"bo.10.mp3","2.8mb","12:26"},
                {"bo.11.mp3","0.8mb","03:17"}
        }));

        parList.add( new Par(
                "בשלח",
                "28.6",
                dedication0,
                "2-shm/04-beshalach/beshalach1.zip,2-shm/04-beshalach/beshalach2.zip",
                "beshalach", new String[][]{
                {"beshalach.01.mp3","2.0mb","08:47"},
                {"beshalach.02.mp3","2.3mb","10:04"},
                {"beshalach.03.mp3","0.9mb","04:07"},
                {"beshalach.04a.mp3","1.9mb","08:22"},
                {"beshalach.04b.mp3","1.7mb","07:36"},
                {"beshalach.05a.mp3","2.4mb","10:33"},
                {"beshalach.05b.mp3","1.7mb","07:23"},
                {"beshalach.06a.mp3","2.1mb","09:03"},
                {"beshalach.06b.mp3","2.1mb","09:17"},
                {"beshalach.07.mp3","2.1mb","09:06"},
                {"beshalach.08.mp3","2.5mb","11:04"},
                {"beshalach.09a.mp3","2.1mb","09:09"},
                {"beshalach.09b.mp3","2.0mb","08:37"},
                {"beshalach.09c.mp3","1.6mb","07:05"},
                {"beshalach.10.mp3","1.0mb","04:27"}
        }));

        parList.add( new Par(
                "יתרו",
                "24.9",
                dedication3,
                "2-shm/05-yitro/yitro1.zip,2-shm/05-yitro/yitro2.zip",
                "yitro", new String[][]{
                {"yitro.01a.mp3","2.4mb","10:22"},
                {"yitro.01b.mp3","2.3mb","09:58"},
                {"yitro.02a.mp3","2.1mb","09:16"},
                {"yitro.02b.mp3","1.9mb","08:13"},
                {"yitro.03.mp3","2.4mb","10:29"},
                {"yitro.04.mp3","2.8mb","12:15"},
                {"yitro.05.mp3","3.1mb","13:24"},
                {"yitro.06a.mp3","2.1mb","09:22"},
                {"yitro.06b.mp3","2.6mb","11:18"},
                {"yitro.07a.mp3","1.8mb","07:45"},
                {"yitro.07b.mp3","1.4mb","06:01"}
        }));

        parList.add( new Par(
                "משפטים",
                "33.6",
                dedication3,
                "2-shm/06-mishpatim/mishpatim1.zip,2-shm/06-mishpatim/mishpatim2.zip",
                "mishpatim", new String[][]{
                {"mishpatim.01a.mp3","2.4mb","10:40"},
                {"mishpatim.01b.mp3","2.4mb","10:28"},
                {"mishpatim.02a.mp3","2.1mb","09:22"},
                {"mishpatim.02b.mp3","2.1mb","09:21"},
                {"mishpatim.03.mp3","1.8mb","07:58"},
                {"mishpatim.04a.mp3","1.5mb","06:27"},
                {"mishpatim.04b.mp3","1.6mb","06:46"},
                {"mishpatim.05.mp3","1.2mb","05:07"},
                {"mishpatim.06a.mp3","1.6mb","07:07"},
                {"mishpatim.06b.mp3","1.4mb","06:17"},
                {"mishpatim.07a.mp3","1.7mb","07:31"},
                {"mishpatim.07b.mp3","1.6mb","06:46"},
                {"mishpatim.08a.mp3","1.7mb","07:32"},
                {"mishpatim.08b.mp3","1.5mb","06:21"},
                {"mishpatim.09a.mp3","1.7mb","07:31"},
                {"mishpatim.09b.mp3","1.8mb","07:41"},
                {"mishpatim.10.mp3","2.1mb","09:19"},
                {"mishpatim.11a.mp3","1.7mb","07:26"},
                {"mishpatim.11b.mp3","1.6mb","06:54"}
        }));

        parList.add( new Par(
                "תרומה",
                "27.4",
                dedication0,
                "2-shm/07-teruma/teruma1.zip,2-shm/07-teruma/teruma2.zip",
                "teruma", new String[][]{
                {"truma.01a.mp3","2.0mb","08:56"},
                {"truma.01b.mp3","1.6mb","06:59"},
                {"truma.02.mp3","1.8mb","08:04"},
                {"truma.03.mp3","1.7mb","07:14"},
                {"truma.04.mp3","2.4mb","10:22"},
                {"truma.05a.mp3","1.9mb","08:10"},
                {"truma.05b.mp3","2.2mb","09:28"},
                {"truma.06.mp3","1.0mb","04:25"},
                {"truma.07a.mp3","1.7mb","07:14"},
                {"truma.07b.mp3","1.5mb","06:36"},
                {"truma.08a.mp3","1.8mb","07:53"},
                {"truma.08b.mp3","1.7mb","07:29"},
                {"truma.09.mp3","1.5mb","06:31"},
                {"truma.10.mp3","2.3mb","09:54"},
                {"truma.11.mp3","2.3mb","10:12"}
        }));

        parList.add( new Par(
                "תצווה",
                "24.8",
                dedication0,
                "2-shm/08-tetzave/tetzave1.zip,2-shm/08-tetzave/tetzave2.zip",
                "tetzave", new String[][]{
                {"tetzave.01.mp3","1.2mb","05:13"},
                {"tetzave.02.mp3","2.0mb","08:42"},
                {"tetzave.03a.mp3","1.7mb","07:31"},
                {"tetzave.03b.mp3","1.5mb","06:38"},
                {"tetzave.04a.mp3","1.7mb","07:16"},
                {"tetzave.04b.mp3","1.8mb","07:38"},
                {"tetzave.05.mp3","1.9mb","08:29"},
                {"tetzave.06.mp3","0.8mb","03:34"},
                {"tetzave.07.mp3","1.5mb","06:27"},
                {"tetzave.08.mp3","1.3mb","05:33"},
                {"tetzave.09.mp3","3.0mb","13:12"},
                {"tetzave.10a.mp3","1.8mb","07:54"},
                {"tetzave.10b.mp3","1.8mb","07:52"},
                {"tetzave.11.mp3","1.3mb","05:30"},
                {"tetzave.12.mp3","1.5mb","06:24"}
        }));

        parList.add( new Par(
                "כי תשא",
                "31.7",
                dedication0,
                "2-shm/09-kitissa/kitissa1.zip,2-shm/09-kitissa/kitissa2.zip",
                "kitissa", new String[][]{
                {"kitissa.01.mp3","3.1mb","13:44"},
                {"kitissa.02.mp3","0.8mb","03:38"},
                {"kitissa.03.mp3","2.4mb","10:28"},
                {"kitissa.04.mp3","1.8mb","07:38"},
                {"kitissa.05.mp3","2.4mb","10:29"},
                {"kitissa.06.mp3","5.6mb","24:20"},
                {"kitissa.07.mp3","3.6mb","15:30"},
                {"kitissa.08.mp3","3.0mb","12:56"},
                {"kitissa.09.mp3","2.3mb","10:15"},
                {"kitissa.10.mp3","2.0mb","08:31"},
                {"kitissa.11.mp3","3.2mb","14:06"},
                {"kitissa.12.mp3","1.5mb","06:39"}
        }));

        parList.add( new Par(
                "ויקהל",
                "7.8",
                dedication0,
                "2-shm/10-vayakhel/vayakhel.zip",
                "vayakhel", new String[][]{
                {"vayakhel.01a.mp3","1.9mb","08:18"},
                {"vayakhel.01b.mp3","1.8mb","07:42"},
                {"vayakhel.02.mp3","1.8mb","08:03"},
                {"vayakhel.03.mp3","2.3mb","10:02"}
        }));

        parList.add( new Par(
                "פקודי",
                "7.7",
                dedication4,
                "2-shm/11-pekudei/pekudei.zip",
                "pekudei", new String[][]{
                {"pekudei.01.mp3","2.5mb","11:03"},
                {"pekudei.02.mp3","3.1mb","13:43"},
                {"pekudei.03.mp3","2.0mb","08:38"}
        }));



        //index 23
        parList.add( new Par(
                "ויקרא",
                "27.3",
                dedication0,
                "3-vay/01-vayikra/vayikra1.zip,3-vay/01-vayikra/vayikra2.zip",
                "vayikra", new String[][]{
                {"vayikra.1a.mp3","2.0mb","08:32"},
                {"vayikra.1b.mp3","2.2mb","09:47"},
                {"vayikra.1c.mp3","2.0mb","08:41"},
                {"vayikra.2.mp3","2.0mb","08:38"},
                {"vayikra.3a.mp3","2.8mb","12:23"},
                {"vayikra.3b.mp3","2.2mb","09:30"},
                {"vayikra.4.mp3","2.6mb","11:19"},
                {"vayikra.5a.mp3","1.5mb","06:26"},
                {"vayikra.5b.mp3","1.5mb","06:29"},
                {"vayikra.6.mp3","1.9mb","08:12"},
                {"vayikra.7.mp3","0.6mb","02:40"},
                {"vayikra.8a.mp3","1.7mb","07:22"},
                {"vayikra.8b.mp3","1.3mb","05:35"},
                {"vayikra.9a.mp3","1.1mb","04:47"},
                {"vayikra.9b.mp3","2.0mb","08:50"}
        }));

        parList.add( new Par(
                "צו",
                "15.0",
                dedication0,
                "3-vay/02-tsav/tsav.zip",
                "tsav", new String[][]{
                {"tsav.1.mp3","2.6mb","11:31"},
                {"tsav.2.mp3","2.9mb","12:34"},
                {"tsav.3.mp3","1.3mb","05:30"},
                {"tsav.4.mp3","2.1mb","09:21"},
                {"tsav.5a.mp3","1.4mb","06:02"},
                {"tsav.5b.mp3","1.6mb","07:05"},
                {"tsav.6.mp3","1.7mb","07:25"},
                {"tsav.7.mp3","1.3mb","05:47"}
        }));

        parList.add( new Par(
                "שמיני",
                "18.3",
                dedication0,
                "3-vay/03-shemini/shemini1.zip,3-vay/03-shemini/shemini2.zip",
                "shemini", new String[][]{
                {"shemini.1.mp3","2.1mb","09:08"},
                {"shemini.2.mp3","1.6mb","07:07"},
                {"shemini.3.mp3","2.0mb","08:35"},
                {"shemini.4a.mp3","1.7mb","07:22"},
                {"shemini.4b.mp3","1.6mb","06:56"},
                {"shemini.5a.mp3","2.2mb","09:35"},
                {"shemini.5b.mp3","2.1mb","09:06"},
                {"shemini.6a.mp3","2.6mb","11:24"},
                {"shemini.6b.mp3","2.4mb","10:30"}
        }));

        parList.add( new Par(
                "תזריע",
                "11.7",
                dedication0,
                "3-vay/04-tazria/tazria.zip",
                "tazria", new String[][]{
                {"tazria.1.mp3","0.8mb","03:21"},
                {"tazria.2a.mp3","2.2mb","09:46"},
                {"tazria.2b.mp3","2.2mb","09:37"},
                {"tazria.3.mp3","1.4mb","06:05"},
                {"tazria.4.mp3","2.8mb","12:23"},
                {"tazria.5.mp3","2.2mb","09:42"}
        }));

        parList.add( new Par(
                "מצורע",
                "14.3",
                dedication0,
                "3-vay/05-metsora/metsora.zip",
                "metsora", new String[][]{
                {"metsora.1.mp3","2.1mb","09:00"},
                {"metsora.2a.mp3","1.6mb","06:50"},
                {"metsora.2b.mp3","1.6mb","06:49"},
                {"metsora.3.mp3","1.8mb","07:54"},
                {"metsora.4.mp3","1.2mb","05:22"},
                {"metsora.5.mp3","2.1mb","09:01"},
                {"metsora.6.mp3","0.9mb","03:54"},
                {"metsora.7.mp3","3.1mb","13:26"}
        }));

        parList.add( new Par(
                "אחרי מות",
                "12.6",
                dedication0,
                "3-vay/06-acharei/acharei.zip",
                "acharei", new String[][]{
                {"acharei.1a.mp3","2.2mb","09:44"},
                {"acharei.1b.mp3","1.7mb","07:15"},
                {"acharei.2a.mp3","1.1mb","04:42"},
                {"acharei.2b.mp3","2.1mb","09:13"},
                {"acharei.3.mp3","1.9mb","08:14"},
                {"acharei.4a.mp3","1.9mb","08:16"},
                {"acharei.4b.mp3","1.8mb","07:38"}
        }));

        parList.add( new Par(
                "קדושים",
                "18.0",
                dedication0,
                "3-vay/07-kedoshim/kedoshim1.zip,3-vay/07-kedoshim/kedoshim2.zip",
                "kedoshim", new String[][]{
                {"kedoshim.01a.mp3","1.5mb","06:39"},
                {"kedoshim.01b.mp3","1.6mb","07:02"},
                {"kedoshim.02a.mp3","1.8mb","07:43"},
                {"kedoshim.02b.mp3","1.8mb","07:52"},
                {"kedoshim.03.mp3","1.7mb","07:16"},
                {"kedoshim.04a.mp3","1.4mb","06:00"},
                {"kedoshim.04b.mp3","1.7mb","07:30"},
                {"kedoshim.05.mp3","1.2mb","05:06"},
                {"kedoshim.06.mp3","1.6mb","06:52"},
                {"kedoshim.07.mp3","2.6mb","11:24"},
                {"kedoshim.08.mp3","1.1mb","05:00"}
        }));

        parList.add( new Par(
                "אמור",
                "20.1",
                dedication0,
                "3-vay/08-emor/emor1.zip,3-vay/08-emor/emor2.zip",
                "emor", new String[][]{
                {"emor.01.mp3","2.5mb","10:43"},
                {"emor.02.mp3","0.8mb","03:16"},
                {"emor.03.mp3","2.0mb","08:42"},
                {"emor.04a.mp3","1.5mb","06:43"},
                {"emor.04b.mp3","1.7mb","07:36"},
                {"emor.05.mp3","1.8mb","07:42"},
                {"emor.06.mp3","1.5mb","06:19"},
                {"emor.07a.mp3","1.6mb","07:06"},
                {"emor.07b.mp3","1.8mb","07:43"},
                {"emor.08.mp3","2.0mb","08:44"},
                {"emor.09.mp3","1.2mb","05:04"},
                {"emor.10.mp3","1.8mb","07:53"}
        }));

        parList.add( new Par(
                "בהר",
                "11.6",
                dedication0,
                "3-vay/09-behar/behar.zip",
                "behar", new String[][]{
                {"behar.1a.mp3","2.0mb","08:46"},
                {"behar.1b.mp3","1.7mb","07:34"},
                {"behar.2.mp3","2.2mb","09:44"},
                {"behar.3a.mp3","1.7mb","07:14"},
                {"behar.3b.mp3","1.4mb","06:12"},
                {"behar.4.mp3","2.5mb","10:53"}
        }));

        //index 32
        parList.add( new Par(
                "בחוקותי",
                "18.6",
                dedication0,
                "3-vay/10-behukotai/behukotai1.zip,3-vay/10-behukotai/behukotai2.zip",
                "behukotai", new String[][]{
                {"behukotai.1a.mp3","1.4mb","05:57"},
                {"behukotai.1b.mp3","1.4mb","06:12"},
                {"behukotai.2.mp3","2.0mb","08:32"},
                {"behukotai.3a.mp3","1.5mb","06:24"},
                {"behukotai.3b.mp3","1.2mb","05:19"},
                {"behukotai.4.mp3","2.6mb","11:17"},
                {"behukotai.5.mp3","2.1mb","09:19"},
                {"behukotai.6.mp3","2.3mb","09:50"},
                {"behukotai.7a.mp3","2.6mb","11:33"},
                {"behukotai.7b.mp3","2.5mb","10:54"}
        }));

        //index 33
        parList.add( new Par(
	"במדבר",
            "10.1",
    dedication5,
	"4-bam/01-bamidbar/bamidbar.zip",
            "bamidbar",
            new String[][]{
            {"bamidbar.1.mp3","2.8mb","12:18"},
            {"bamidbar.2.mp3","1.3mb","05:45"},
            {"bamidbar.3a.mp3","1.6mb","06:46"},
            {"bamidbar.3b.mp3","1.7mb","07:18"},
            {"bamidbar.4.mp3","0.9mb","03:45"},
            {"bamidbar.5.mp3","1.9mb","08:20"}
            }));

    parList.add( new Par(
	"נשא",
            "15.7",
    dedication6,
	"4-bam/02-nasso/nasso.zip",
            "nasso", new String[][]{
            {"nasso.1.mp3","1.8mb","08:04"},
            {"nasso.2.mp3","1.8mb","07:40"},
            {"nasso.3a.mp3","2.3mb","10:15"},
            {"nasso.3b.mp3","2.4mb","10:24"},
            {"nasso.4.mp3","2.7mb","11:57"},
            {"nasso.5.mp3","0.6mb","02:35"},
            {"nasso.6a.mp3","1.9mb","08:14"},
            {"nasso.6b.mp3","2.1mb","09:10"}
            }));

    parList.add( new Par(
	"בהעלותך",
            "20.9",
    dedication7,
	"4-bam/03-behaalotcha/behaalotcha1.zip,4-bam/03-behaalotcha/behaalotcha2.zip",
            "behaalotcha", 	new String[][]{
            {"behaalotcha.1.mp3","1.6mb","06:53"},
            {"behaalotcha.2.mp3","2.1mb","09:04"},
            {"behaalotcha.3.mp3","1.3mb","05:46"},
            {"behaalotcha.4.mp3","0.9mb","03:42"},
            {"behaalotcha.5.mp3","2.3mb","10:15"},
            {"behaalotcha.6.mp3","2.4mb","10:35"},
            {"behaalotcha.7.mp3","2.5mb","11:03"},
            {"behaalotcha.8a.mp3","2.0mb","08:42"},
            {"behaalotcha.8b.mp3","2.4mb","10:15"},
            {"behaalotcha.9a.mp3","1.6mb","07:09"},
            {"behaalotcha.9b.mp3","1.8mb","07:55"}
            }));

    parList.add( new Par(
	"שלח",
            "17.1",
    dedication7,
	"4-bam/04-shelach/shelach.zip",
            "shelach", new String[][]{
            {"shelach.1.mp3","2.0mb","08:43"},
            {"shelach.2.mp3","3.5mb","15:09"},
            {"shelach.3.mp3","0.9mb","03:43"},
            {"shelach.4a.mp3","2.0mb","08:33"},
            {"shelach.4b.mp3","2.8mb","12:24"},
            {"shelach.5.mp3","2.1mb","09:08"},
            {"shelach.6.mp3","1.5mb","06:34"},
            {"shelach.7.mp3","2.3mb","10:11"}
            }));

    parList.add( new Par(
	"קורח",
            "14.4",
    dedication8,
	"4-bam/05-korach/korach.zip",
            "korach", new String[][]{
            {"korach.1a.mp3","2.4mb","10:32"},
            {"korach.1b.mp3","3.0mb","13:09"},
            {"korach.2.mp3","1.6mb","06:45"},
            {"korach.3.mp3","1.7mb","07:20"},
            {"korach.4.mp3","1.6mb","06:46"},
            {"korach.5.mp3","2.7mb","11:57"},
            {"korach.6.mp3","1.4mb","06:16"}
            }));

    parList.add( new Par(
	"חוקת",
            "18.1",
    dedication0,
	"4-bam/06-chukat/chukat.zip",
            "chukat", new String[][]{
            {"chukat.1a.mp3","2.5mb","10:56"},
            {"chukat.1b.mp3","2.4mb","10:27"},
            {"chukat.2.mp3","2.7mb","11:41"},
            {"chukat.3.mp3","1.3mb","05:47"},
            {"chukat.4.mp3","1.6mb","07:09"},
            {"chukat.5.mp3","2.6mb","11:09"},
            {"chukat.6.mp3","2.7mb","11:39"},
            {"chukat.7.mp3","2.4mb","10:19"}
            }));

    parList.add( new Par(
	"בלק",
            "20.6",
    dedication9,
	"4-bam/07-balak/balak.zip",
            "balak", new String[][]{
            {"balak.1.mp3","3.2mb","13:52"},
            {"balak.2.mp3","1.0mb","04:09"},
            {"balak.3.mp3","3.4mb","14:41"},
            {"balak.4.mp3","2.4mb","10:33"},
            {"balak.5a.mp3","1.6mb","07:03"},
            {"balak.5b.mp3","2.3mb","10:10"},
            {"balak.6.mp3","2.9mb","12:40"},
            {"balak.7.mp3","2.2mb","09:42"},
            {"balak.8.mp3","1.6mb","07:09"}
            }));

    parList.add( new Par(
	"פנחס",
            "21.8",
    dedication0,
	"4-bam/08-pinchas/pinchas1.zip,4-bam/08-pinchas/pinchas2.zip",
            "pinchas", new String[][]{
            {"pinchas.1.mp3","2.6mb","11:10"},
            {"pinchas.2a.mp3","2.0mb","08:31"},
            {"pinchas.2b.mp3","2.5mb","11:02"},
            {"pinchas.3.mp3","2.4mb","10:29"},
            {"pinchas.4.mp3","2.3mb","10:01"},
            {"pinchas.5.mp3","3.2mb","13:51"},
            {"pinchas.6.mp3","2.6mb","11:14"},
            {"pinchas.7.mp3","1.8mb","07:55"},
            {"pinchas.8.mp3","2.5mb","10:47"}
            }));

    parList.add( new Par(
	"מטות",
            "14.2",
    dedication0,
	"4-bam/09-matot/matot.zip",
            "matot", new String[][]{
            {"matot.1a.mp3","1.6mb","07:00"},
            {"matot.1b.mp3","1.9mb","08:17"},
            {"matot.2a.mp3","1.3mb","05:40"},
            {"matot.2b.mp3","1.7mb","07:23"},
            {"matot.3.mp3","2.4mb","10:35"},
            {"matot.4.mp3","1.5mb","06:37"},
            {"matot.5.mp3","1.4mb","06:04"},
            {"matot.6.mp3","2.4mb","10:19"}
            }));

    parList.add( new Par(
	"מסעי",
            "12.2",
    dedication0,
	"4-bam/10-massei/massei.zip",
            "massei", new String[][]{
            {"massei.1.mp3","2.9mb","12:36"},
            {"massei.2.mp3","1.0mb","04:26"},
            {"massei.3a.mp3","1.8mb","07:47"},
            {"massei.3b.mp3","1.7mb","07:37"},
            {"massei.4a.mp3","2.0mb","08:38"},
            {"massei.4b.mp3","1.9mb","08:25"},
            {"massei.5.mp3","0.9mb","03:54"}
            }));


    //index 43
        parList.add( new Par(
	"דברים",
            "20.4",
    dedication0,
	"5-dev/01-devarim/devarim1.zip,5-dev/01-devarim/devarim2.zip",
            "devarim", new String[][]{
            {"devarim01a.mp3","1.9mb","08:27"},
            {"devarim01b.mp3","1.9mb","08:24"},
            {"devarim02.mp3","1.2mb","05:26"},
            {"devarim03a.mp3","2.5mb","10:54"},
            {"devarim03b.mp3","2.4mb","10:35"},
            {"devarim04.mp3","2.4mb","10:21"},
            {"devarim05.mp3","1.4mb","05:56"},
            {"devarim06.mp3","1.6mb","06:58"},
            {"devarim07a.mp3","2.0mb","08:36"},
            {"devarim07b.mp3","2.1mb","09:15"},
            {"devarim08.mp3","0.9mb","03:57"}
            }));

    parList.add( new Par(
	"ואתחנן",
            "16.3",
    dedication0,
	"5-dev/02-vaetchanan/vaetchanan.zip",
            "vaetchanan", new String[][]{
            {"vaetchanan.01a.mp3","2.2mb","09:24"},
            {"vaetchanan.01b.mp3","1.3mb","05:37"},
            {"vaetchanan.02a.mp3","2.4mb","10:37"},
            {"vaetchanan.02b.mp3","2.6mb","11:11"},
            {"vaetchanan.03a.mp3","1.6mb","06:46"},
            {"vaetchanan.03b.mp3","1.7mb","07:14"},
            {"vaetchanan.04.mp3","2.4mb","10:39"},
            {"vaetchanan.05.mp3","2.2mb","09:32"}
            }));

    parList.add( new Par(
	"עקב",
            "19.8",
    dedication0,
	"5-dev/03-ekev/ekev1.zip,5-dev/03-ekev/ekev2.zip",
            "ekev", new String[][]{
            {"ekev.1a.mp3","2.7mb","11:54"},
            {"ekev.2a.mp3","2.2mb","09:26"},
            {"ekev.3a.mp3","2.7mb","11:39"},
            {"ekev.4a.mp3","2.6mb","11:28"},
            {"ekev.5a.mp3","1.9mb","08:17"},
            {"ekev.6a.mp3","1.0mb","04:33"},
            {"ekev.7a.mp3","2.6mb","11:29"},
            {"ekev.7b.mp3","2.7mb","11:51"},
            {"ekev.8a.mp3","1.3mb","05:37"}
            }));

    parList.add( new Par(
	"ראה",
            "29.4",
    dedication0,
	"5-dev/04-ree/ree1.zip,5-dev/04-ree/ree2.zip",
            "reeh", new String[][]{
            {"ree.01.mp3","1.6mb","06:55"},
            {"ree.02a.mp3","4.0mb","17:16"},
            {"ree.02b.mp3","2.3mb","10:09"},
            {"ree.03.mp3","2.8mb","12:25"},
            {"ree.04.mp3","1.0mb","04:22"},
            {"ree.05.mp3","3.2mb","13:52"},
            {"ree.06.mp3","3.9mb","17:02"},
            {"ree.07.mp3","2.2mb","09:44"},
            {"ree.08.mp3","2.4mb","10:23"},
            {"ree.09.mp3","1.1mb","04:36"},
            {"ree.10.mp3","1.1mb","04:58"},
            {"ree.11a.mp3","1.6mb","06:46"},
            {"ree.11b.mp3","2.2mb","09:42"}
            }));

    parList.add( new Par(
	"שופטים",
            "21.7",
    dedication0,
	"5-dev/05-shoftim/shoftim1.zip,5-dev/05-shoftim/shoftim2.zip",
            "shoftim", new String[][]{
            {"shoftim.01a.mp3","1.4mb","06:04"},
            {"shoftim.02a.mp3","0.6mb","02:32"},
            {"shoftim.03a.mp3","1.1mb","04:50"},
            {"shoftim.04a.mp3","1.2mb","05:23"},
            {"shoftim.05a.mp3","1.3mb","05:40"},
            {"shoftim.06a.mp3","1.2mb","05:22"},
            {"shoftim.06b.mp3","1.9mb","08:06"},
            {"shoftim.07a.mp3","2.3mb","09:53"},
            {"shoftim.08a.mp3","1.9mb","08:12"},
            {"shoftim.09a.mp3","1.1mb","04:57"},
            {"shoftim.09b.mp3","1.6mb","06:51"},
            {"shoftim.10a.mp3","2.3mb","10:14"},
            {"shoftim.10b.mp3","2.4mb","10:40"},
            {"shoftim.11a.mp3","1.3mb","05:38"}
            }));

    parList.add( new Par(
	"כי תצא",
            "19.2",
    dedication0,
	"5-dev/06-kitetze/kitetze1.zip,5-dev/06-kitetze/kitetze2.zip",
            "kitetze", new String[][]{
            {"kitetze.01a.mp3","1.2mb","05:19"},
            {"kitetze.02.mp3","1.6mb","07:11"},
            {"kitetze.03.mp3","1.7mb","07:35"},
            {"kitetze.04a.mp3","1.0mb","04:34"},
            {"kitetze.05.mp3","1.7mb","07:29"},
            {"kitetze.06.mp3","1.7mb","07:31"},
            {"kitetze.07.mp3","2.4mb","10:39"},
            {"kitetze.08.mp3","1.2mb","05:01"},
            {"kitetze.09.mp3","1.5mb","06:21"},
            {"kitetze.10.mp3","1.6mb","06:51"},
            {"kitetze.11a.mp3","1.6mb","07:03"},
            {"kitetze.11b.mp3","1.9mb","08:07"}
            }));

    parList.add( new Par(
	"כי תבוא",
            "14.3",
    dedication0,
	"5-dev/07-kitavo/kitavo.zip",
            "kitavo", new String[][]{
            {"kitavo.1.mp3","2.3mb","10:04"},
            {"kitavo.2.mp3","2.7mb","11:40"},
            {"kitavo.3a.mp3","1.4mb","06:17"},
            {"kitavo.3b.mp3","1.6mb","07:02"},
            {"kitavo.4.mp3","2.6mb","11:10"},
            {"kitavo.5.mp3","2.8mb","12:18"},
            {"kitavo.6.mp3","0.9mb","04:00"}
            }));

    parList.add( new Par(
	"ניצבים",
            "7.4",
    dedication0,
	"5-dev/08-nitzavim/nitzavim.zip",
            "nitzavim", new String[][]{
            {"nitzavim.1.mp3","2.7mb","11:57"},
            {"nitzavim.2.mp3","2.2mb","09:43"},
            {"nitzavim.3.mp3","2.5mb","10:43"}
            }));

    parList.add( new Par(
	"וילך",
            "4.9",
    dedication0,
	"5-dev/09-vayelech/vayelech.zip",
            "vayelech", new String[][]{
            {"vayelech.1.mp3","2.2mb","09:30"},
            {"vayelech.2.mp3","2.7mb","11:53"}
            }));

    parList.add( new Par(
	"האזינו",
            "17.3",
    dedication0,
	"5-dev/10-haazinu/haazinu.zip",
            "haazinu", new String[][]{
            {"haazinu.1.mp3","3.2mb","13:51"},
            {"haazinu.2.mp3","2.5mb","10:45"},
            {"haazinu.3.mp3","2.0mb","08:54"},
            {"haazinu.4.mp3","2.9mb","12:40"},
            {"haazinu.5.mp3","1.9mb","08:19"},
            {"haazinu.6.mp3","2.8mb","12:09"},
            {"haazinu.7.mp3","2.0mb","08:45"}
            }));

    parList.add( new Par(
	"וזאת הברכה",
            "15.2",
    dedication0,
	"5-dev/11-vezot/vezot.zip",
            "vezot", new String[][]{
            {"vezot.1a.mp3","2.4mb","10:23"},
            {"vezot.1b.mp3","1.5mb","06:27"},
            {"vezot.2.mp3","1.8mb","07:41"},
            {"vezot.3.mp3","1.4mb","06:03"},
            {"vezot.4.mp3","2.0mb","08:31"},
            {"vezot.5.mp3","2.5mb","10:47"},
            {"vezot.6.mp3","1.1mb","04:48"},
            {"vezot.7.mp3","2.7mb","11:44"}
            }));
    }

    public static SparseArray<List<Par>> parMap = new SparseArray<>();

    static {
        parMap.put(0, parList.subList(0, 12));
        parMap.put(1, parList.subList(12, 23));
        parMap.put(2, parList.subList(23, 33));
        parMap.put(3, parList.subList(33, 43));
        parMap.put(4, parList.subList(43, parList.size()));

    }


}
