package actparks.parksapp.WalkDatabaseFiles;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import actparks.parksapp.ContactDatabaseFiles.Contact;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#6

@Database(entities = {Walk.class}, version = 1)
public abstract class WalkRoomDatabase extends RoomDatabase{

    // TODO: Delete when done
    static String temp_route = "-35.273569,149.12181,568.0__,__-35.273569,149.12181,568.0__,__-35.273569,149.12181,568.0__,__-35.273569,149.12181,568.0__,__-35.273569,149.12181,568.0__,__-35.273569,149.12181,568.0__,__-35.273563,149.121819,568.0__,__-35.273559,149.121816,568.0__,__-35.27356,149.121811,568.0__,__-35.273555,149.121803,568.0__,__-35.27354,149.12179,568.0__,__-35.273491,149.121779,568.0__,__-35.273495,149.121793,568.0__,__-35.273499,149.121801,568.0__,__-35.273499,149.121808,568.0__,__-35.273502,149.121818,568.0__,__-35.273502,149.12182,568.0__,__-35.273509,149.121826,568.0__,__-35.273506,149.121828,568.0__,__-35.273507,149.12183,568.0__,__-35.273507,149.121835,568.0__,__-35.273515,149.121839,568.0__,__-35.273515,149.12184,568.0__,__-35.273517,149.121841,568.0__,__-35.273526,149.121844,568.0__,__-35.273536,149.121846,568.0__,__-35.273537,149.121846,568.0__,__-35.273544,149.121843,568.0__,__-35.273548,149.121845,568.0__,__-35.27355,149.121845,568.0__,__-35.27355,149.121845,568.0__,__-35.27355,149.121845,568.0__,__-35.273548,149.121845,568.0__,__-35.273512,149.12177,568.4__,__-35.273486,149.121702,568.7__,__-35.273481,149.121677,569.0__,__-35.273379,149.121471,568.9__,__-35.273359,149.121412,568.9__,__-35.273346,149.121381,568.9__,__-35.273332,149.121349,568.8__,__-35.273321,149.121315,568.8__,__-35.273309,149.121283,568.8__,__-35.273297,149.121251,568.8__,__-35.273287,149.121219,568.7__,__-35.273275,149.121186,568.7__,__-35.273266,149.121153,568.7__,__-35.273255,149.121116,568.7__,__-35.273248,149.12108,568.7__,__-35.273241,149.121041,568.7__,__-35.273234,149.121002,568.6__,__-35.273226,149.120963,568.6__,__-35.273218,149.120925,568.6__,__-35.273195,149.120784,568.6__,__-35.273187,149.120709,568.5__,__-35.273184,149.120636,568.4__,__-35.273171,149.120496,568.3__,__-35.273165,149.12046,568.3__,__-35.27315,149.120364,568.1__,__-35.27314,149.120302,568.1__,__-35.273117,149.120162,568.1__,__-35.273108,149.120059,568.2__,__-35.273094,149.119949,568.3__,__-35.273087,149.119914,568.3__,__-35.273074,149.11985,568.4__,__-35.27305,149.119719,568.5__,__-35.273023,149.119577,568.7__,__-35.273018,149.119542,568.7__,__-35.273014,149.119505,568.8__,__-35.272993,149.11938,568.9__,__-35.272945,149.119159,569.2__,__-35.272917,149.118937,569.5__,__-35.272893,149.118687,570.0__,__-35.272871,149.118461,570.6__,__-35.272831,149.118215,572.5__,__-35.272796,149.117983,574.6__,__-35.272743,149.117751,575.8__,__-35.272695,149.117524,577.2__,__-35.272667,149.117473,577.6__,__-35.272608,149.117425,578.0__,__-35.272593,149.117409,578.0__,__-35.272566,149.117367,578.0__,__-35.272555,149.117344,578.0__,__-35.272543,149.117317,578.0__,__-35.272533,149.11729,578.0__,__-35.272521,149.117263,578.0__,__-35.272505,149.117028,578.0__,__-35.272652,149.116898,577.9__,__-35.272797,149.116762,577.9__,__-35.272837,149.116725,577.9__,__-35.272856,149.116703,577.8__,__-35.272876,149.116681,577.8__,__-35.27298,149.116574,577.8__,__-35.273021,149.116532,577.8__,__-35.273166,149.116385,577.6__,__-35.273243,149.116299,577.7__,__-35.273284,149.116256,577.7__,__-35.273324,149.116217,577.6__,__-35.273359,149.116177,577.6__,__-35.27342,149.116124,577.4__,__-35.273489,149.116069,577.4__,__-35.273646,149.11592,578.5__,__-35.273707,149.115855,579.1__,__-35.273747,149.115812,579.6__,__-35.273768,149.115794,580.0__,__-35.273813,149.115757,580.4__,__-35.27386,149.115713,580.4__,__-35.27401,149.115559,580.8__,__-35.274042,149.115444,580.6__,__-35.274089,149.115333,580.5__,__-35.274062,149.115107,580.0__,__-35.273966,149.114896,581.9__,__-35.273894,149.114709,581.9__,__-35.273769,149.114552,582.0__,__-35.273742,149.114517,582.0__,__-35.273706,149.114482,582.0__,__-35.273686,149.11447,582.0__,__-35.273569,149.114444,582.0__,__-35.27355,149.114438,582.0__,__-35.273515,149.114415,582.0__,__-35.273452,149.114209,583.4__,__-35.273425,149.114151,584.2__,__-35.2734,149.114077,585.1__,__-35.273368,149.113998,586.1__,__-35.273311,149.113871,586.9__,__-35.273206,149.113684,588.0__,__-35.27315,149.113607,588.0__,__-35.273008,149.113431,589.5__,__-35.272966,149.11337,590.2__,__-35.272866,149.113175,591.8__,__-35.272759,149.112999,593.3__,__-35.272638,149.1128,595.0__,__-35.272593,149.112713,595.4__,__-35.272539,149.112589,595.9__,__-35.272489,149.112485,596.0__,__-35.272436,149.112381,596.1__,__-35.272404,149.112329,596.1__,__-35.272372,149.112267,596.2__,__-35.272337,149.11218,596.3__,__-35.272308,149.112115,596.4__,__-35.272218,149.111965,596.7__,__-35.272131,149.111835,597.1__,__-35.272118,149.111809,597.2__,__-35.272107,149.111784,597.3__,__-35.272097,149.11176,597.3__,__-35.27207,149.111678,598.0__,__-35.27206,149.111651,601.0__,__-35.271999,149.111493,601.1__,__-35.271998,149.111464,601.1__,__-35.271995,149.111436,601.2__,__-35.272009,149.111189,602.4__,__-35.272008,149.111042,603.6__,__-35.271996,149.110959,604.3__,__-35.271916,149.110757,606.0__,__-35.271905,149.110735,606.0__,__-35.271891,149.110713,606.0__,__-35.271722,149.110615,606.9__,__-35.271611,149.110582,607.2__,__-35.271591,149.110575,606.7__,__-35.271516,149.110553,607.0__,__-35.271345,149.110512,607.8__,__-35.271168,149.110453,608.4__,__-35.27105,149.110292,610.0__,__-35.27102,149.110252,610.0__,__-35.270906,149.110049,611.2__,__-35.270862,149.109951,613.1__,__-35.270828,149.10988,613.6__,__-35.270727,149.109694,614.6__,__-35.270646,149.109483,615.7__,__-35.27057,149.109269,616.9__,__-35.270554,149.109079,618.8__,__-35.270535,149.109028,619.4__,__-35.270509,149.108939,620.3__,__-35.270503,149.108908,620.4__,__-35.270477,149.108755,621.5__,__-35.270438,149.108518,624.6__,__-35.270456,149.10828,627.7__,__-35.270527,149.108076,630.6__,__-35.270633,149.10787,632.6__,__-35.27067,149.107805,632.9__,__-35.270782,149.107618,634.8__,__-35.270832,149.107526,636.6__,__-35.2709,149.107321,640.2__,__-35.270977,149.107117,642.5__,__-35.27103,149.106897,644.3__,__-35.271072,149.10676,644.9__,__-35.271081,149.106736,644.9__,__-35.271129,149.10655,645.5__,__-35.271193,149.106343,646.0__,__-35.271293,149.106133,646.6__,__-35.271393,149.105931,647.0__,__-35.271407,149.105852,647.1__,__-35.271326,149.105653,647.2__,__-35.271309,149.105406,647.4__,__-35.271353,149.105191,648.0__,__-35.271358,149.105184,648.0__,__-35.271371,149.105154,648.2__,__-35.271437,149.104939,650.2__,__-35.271448,149.10486,651.0__,__-35.271447,149.104785,651.8__,__-35.271442,149.104716,652.6__,__-35.271433,149.104643,653.2__,__-35.271426,149.104643,653.2__,__-35.271411,149.104648,653.3__,__-35.271407,149.104646,653.3__,__-35.271415,149.104669,655.0__,__-35.271419,149.104672,655.1__,__-35.271292,149.104634,657.5__,__-35.271272,149.104604,657.8__,__-35.271262,149.104597,658.0__,__-35.271249,149.104589,658.1__,__-35.271218,149.104572,658.8__,__-35.271037,149.104486,660.8__,__-35.270926,149.104397,662.7__,__-35.270909,149.104382,662.9__,__-35.270892,149.104367,663.0__,__-35.270859,149.10434,663.2__,__-35.270707,149.104189,665.4__,__-35.270642,149.104124,666.2__,__-35.270627,149.10411,666.5__,__-35.270611,149.104094,666.7__,__-35.270596,149.104075,667.0__,__-35.270584,149.104056,667.3__,__-35.270498,149.103971,668.1__,__-35.270373,149.1038,670.0__,__-35.270326,149.103734,670.4__,__-35.270209,149.103559,671.2__,__-35.270138,149.103418,672.1__,__-35.270037,149.103206,672.4__,__-35.269972,149.103059,672.6__,__-35.269949,149.103008,672.6__,__-35.269873,149.102808,672.7__,__-35.269782,149.102617,672.8__,__-35.269754,149.10258,672.9__,__-35.269664,149.102377,673.0__,__-35.269638,149.102325,673.1__,__-35.269629,149.102295,673.2__,__-35.269619,149.102269,673.3__,__-35.269605,149.102226,673.3__,__-35.269589,149.102178,673.4__,__-35.269495,149.101962,674.1__,__-35.269407,149.101759,675.3__,__-35.269392,149.101703,675.5__,__-35.269378,149.101615,675.9__,__-35.269375,149.101587,676.0__,__-35.269347,149.101489,676.8__,__-35.269332,149.101378,678.3__,__-35.269319,149.101136,680.3__,__-35.269312,149.101011,681.1__,__-35.269316,149.100777,683.3__,__-35.26932,149.100538,685.7__,__-35.269278,149.100309,687.7__,__-35.26927,149.100263,687.9__,__-35.269263,149.10014,688.8__,__-35.269212,149.099911,690.4__,__-35.269191,149.099667,692.8__,__-35.269175,149.099478,694.4__,__-35.269186,149.099252,695.7__,__-35.269224,149.099016,697.5__,__-35.269216,149.098789,697.9__,__-35.269119,149.098599,698.2__,__-35.269036,149.098402,698.3__,__-35.269006,149.098163,698.3__,__-35.269016,149.09809,698.3__,__-35.26902,149.098041,698.2__,__-35.269025,149.097929,698.1__,__-35.269026,149.097883,698.1__,__-35.269026,149.097728,698.0__,__-35.269032,149.097578,698.0__,__-35.269056,149.097508,698.0__,__-35.269062,149.097491,698.0__,__-35.269074,149.097462,698.0__,__-35.269086,149.097454,698.0__,__-35.269124,149.097499,698.0__,__-35.26928,149.09763,701.4__,__-35.269418,149.097786,706.3__,__-35.269569,149.097934,710.6__,__-35.26975,149.097965,714.0__,__-35.269888,149.097937,719.5__,__-35.269925,149.097951,721.4__,__-35.269995,149.097961,724.0__,__-35.270022,149.097963,725.2__,__-35.27007,149.09795,727.8__,__-35.270096,149.097946,729.0__,__-35.270135,149.097961,730.6__,__-35.270176,149.097961,732.2__,__-35.270283,149.097954,736.1__,__-35.270297,149.097947,736.6__,__-35.270323,149.097958,737.7__,__-35.27038,149.097967,739.4__,__-35.270536,149.097963,743.7__,__-35.270539,149.097962,743.8__,__-35.270544,149.097962,743.9__,__-35.270564,149.097958,744.6__,__-35.270573,149.097958,744.9__,__-35.270581,149.097958,745.2__,__-35.270665,149.097977,747.7__,__-35.27085,149.097957,754.1__,__-35.27093,149.097923,756.2__,__-35.270936,149.097913,756.5__,__-35.270948,149.097877,757.6__,__-35.270953,149.097862,758.0__,__-35.270959,149.097854,757.8__,__-35.270964,149.097844,757.6__,__-35.270983,149.097832,757.0__,__-35.271013,149.097806,755.7__,__-35.271022,149.097775,754.5__,__-35.27104,149.09769,753.6__,__-35.271069,149.097647,752.9__,__-35.271139,149.09751,750.1__,__-35.271192,149.097445,748.9__,__-35.271244,149.097425,748.3__,__-35.271317,149.097416,748.0__,__-35.271349,149.097408,748.1__,__-35.271534,149.097419,752.3__,__-35.271711,149.097369,757.9__,__-35.271896,149.097385,763.1__,__-35.272053,149.097345,767.9__,__-35.272233,149.097278,773.4__,__-35.272312,149.097255,777.2__,__-35.272327,149.097251,777.8__,__-35.272353,149.097253,778.8__,__-35.272363,149.097257,779.2__,__-35.272372,149.097259,779.5__,__-35.27238,149.097262,779.8__,__-35.272441,149.097288,782.6__,__-35.272503,149.097327,784.9__,__-35.272538,149.097346,785.7__,__-35.27271,149.097368,789.5__,__-35.272733,149.097366,790.1__,__-35.27274,149.097365,790.4__,__-35.272806,149.097385,792.8__,__-35.27286,149.097402,794.0__,__-35.27289,149.09741,794.7__,__-35.2729,149.097411,795.0__,__-35.272909,149.097413,795.3__,__-35.272933,149.09741,796.1__,__-35.272955,149.097407,796.8__,__-35.273033,149.097437,799.4__,__-35.273043,149.097445,799.8__,__-35.273052,149.097452,800.0__,__-35.273062,149.097457,800.3__,__-35.273079,149.097456,800.7__,__-35.273088,149.097456,800.9__,__-35.273108,149.097446,801.5__,__-35.273118,149.097438,801.7__,__-35.273126,149.097434,802.0__,__-35.273134,149.097429,802.2__,__-35.273143,149.097423,802.5__,__-35.273272,149.097396,803.4__,__-35.273364,149.097412,804.0__,__-35.273535,149.097493,806.1__,__-35.273669,149.097535,808.4__,__-35.273795,149.097522,809.1__,__-35.273795,149.097514,809.2__,__-35.273792,149.097522,809.2__,__-35.273792,149.097531,809.2__,__-35.273848,149.097616,809.3__,__-35.273877,149.097637,809.2__,__-35.27393,149.097693,808.1__,__-35.273996,149.097771,806.9__,__-35.274008,149.09778,806.7__,__-35.274083,149.097823,805.6__,__-35.274105,149.097834,805.3__,__-35.274224,149.097869,804.0__,__-35.274401,149.097896,804.1__,__-35.274557,149.09789,804.1__,__-35.274748,149.097886,804.0__,__-35.274841,149.097866,804.0__,__-35.274921,149.097851,803.7__,__-35.275003,149.097749,801.7__,__-35.274998,149.097693,800.9__,__-35.275021,149.097672,800.4__,__-35.275028,149.097652,800.0__,__-35.275028,149.097608,799.4__,__-35.275005,149.09751,797.6__,__-35.274948,149.097457,795.5__,__-35.274894,149.097397,795.0__,__-35.274885,149.097385,795.0__,__-35.274875,149.097378,795.0__,__-35.274864,149.09737,795.0__,__-35.274757,149.097184,794.6__,__-35.274748,149.09717,794.5__,__-35.274738,149.09716,794.4__,__-35.274728,149.097151,794.4__,__-35.27472,149.097142,794.3__,__-35.27471,149.097128,794.3__,__-35.274693,149.097103,794.2__,__-35.274667,149.097068,794.0__,__-35.274614,149.096978,793.6__,__-35.27461,149.096882,793.3__,__-35.274682,149.096675,792.7__,__-35.274704,149.096454,792.1__,__-35.274715,149.096385,791.9__,__-35.274715,149.096268,791.5__,__-35.274726,149.096037,790.5__,__-35.274728,149.095809,789.3__,__-35.274718,149.095751,789.0__,__-35.274701,149.095517,789.0__,__-35.274808,149.095319,787.2__,__-35.274828,149.095305,786.8__,__-35.274849,149.095295,786.3__,__-35.274897,149.095286,785.3__,__-35.274921,149.095286,784.9__,__-35.274945,149.095288,784.4__,__-35.274968,149.095293,783.9__,__-35.274992,149.095302,783.4__,__-35.275097,149.095366,782.4__,__-35.275129,149.095403,782.1__,__-35.275187,149.095507,781.3__,__-35.275197,149.095729,779.9__,__-35.275189,149.095774,779.6__,__-35.275167,149.095868,778.9__,__-35.275163,149.095887,778.8__,__-35.275151,149.095951,778.3__,__-35.275142,149.095997,778.0__,__-35.275118,149.096137,776.9__,__-35.275115,149.096163,776.6__,__-35.275103,149.096236,776.1__,__-35.275096,149.096327,775.3__,__-35.275094,149.096351,775.2__,__-35.275108,149.096421,774.6__,__-35.275155,149.096593,773.0__,__-35.275183,149.096641,772.6__,__-35.275293,149.096743,771.4__,__-35.275318,149.096753,771.2__,__-35.275511,149.096795,770.1__,__-35.27561,149.096802,769.5__,__-35.275804,149.09683,768.4__,__-35.275997,149.096862,767.3__,__-35.276127,149.096904,766.6__,__-35.276205,149.096923,766.1__,__-35.276386,149.09698,765.2__,__-35.276439,149.096998,764.9__,__-35.27661,149.097094,764.0__,__-35.276657,149.09712,763.7__,__-35.276813,149.097239,762.6__,__-35.27688,149.097296,762.1__,__-35.277041,149.097449,760.6__,__-35.277169,149.097631,759.1__,__-35.277301,149.09782,757.7__,__-35.277447,149.097995,756.2__,__-35.277519,149.098088,755.5__,__-35.277681,149.098235,754.2__,__-35.277862,149.098326,753.3__,__-35.278049,149.098383,758.3__,__-35.278199,149.098396,760.5__,__-35.278344,149.098365,758.9__,__-35.278524,149.098258,756.7__,__-35.278692,149.098109,754.4__,__-35.278866,149.097982,752.0__,__-35.279032,149.097849,749.2__,__-35.279199,149.097727,746.7__,__-35.27939,149.097653,744.0__,__-35.279502,149.097617,742.2__,__-35.279702,149.097591,739.1__,__-35.279889,149.097634,735.7__,__-35.28008,149.097678,732.3__,__-35.280234,149.097734,729.4__,__-35.280409,149.097786,726.2__,__-35.2806,149.097799,722.9__,__-35.280787,149.097744,720.1__,__-35.280981,149.097732,716.9__,__-35.281054,149.097737,715.6__,__-35.281063,149.097737,715.4__,__-35.281103,149.097743,715.0__,__-35.281115,149.097752,714.9__,__-35.281132,149.097761,714.9__,__-35.281215,149.097831,714.4__,__-35.281283,149.097917,713.9__,__-35.28133,149.098128,712.9__,__-35.281286,149.09837,711.7__,__-35.281229,149.098591,710.4__,__-35.281171,149.098751,709.4__,__-35.281076,149.098975,707.9__,__-35.280951,149.099096,706.9__,__-35.280901,149.099116,706.6__,__-35.280722,149.099161,705.6__,__-35.280632,149.099193,704.8__,__-35.280466,149.0993,703.1__,__-35.28032,149.099464,701.6__,__-35.280171,149.09964,700.3__,__-35.280055,149.099799,699.2__,__-35.279976,149.099909,698.4__,__-35.2799,149.100017,697.7__,__-35.27982,149.100182,696.1__,__-35.279727,149.100362,694.3__,__-35.279652,149.100564,693.0__,__-35.279611,149.10076,691.8__,__-35.279573,149.100955,690.7__,__-35.279519,149.101165,689.4__,__-35.279447,149.101393,687.9__,__-35.279373,149.101606,686.4__,__-35.279318,149.101815,685.2__,__-35.279287,149.102064,683.9__,__-35.279273,149.102285,682.8__,__-35.279269,149.102518,681.6__,__-35.279252,149.102762,680.4__,__-35.279215,149.102996,679.1__,__-35.279179,149.103229,677.7__,__-35.279117,149.103452,676.4__,__-35.279081,149.103577,675.6__,__-35.278982,149.103762,674.5__,__-35.278837,149.103925,673.1__,__-35.278648,149.104026,671.3__,__-35.278458,149.104074,669.3__,__-35.278273,149.104125,667.9__,__-35.2781,149.104194,667.0__,__-35.277933,149.104318,665.1__,__-35.277897,149.104357,664.5__,__-35.277832,149.104504,662.6__,__-35.277809,149.104549,662.0__,__-35.277774,149.104626,661.0__,__-35.277763,149.104651,660.7__,__-35.277727,149.10473,659.8__,__-35.277635,149.104945,657.4__,__-35.277626,149.104973,657.1__,__-35.277547,149.105192,655.0__,__-35.277479,149.105399,652.9__,__-35.277451,149.105491,651.9__,__-35.277435,149.105552,651.3__,__-35.277413,149.10571,649.8__,__-35.277438,149.105955,647.5__,__-35.277487,149.106182,645.5__,__-35.277552,149.106398,643.5__,__-35.277621,149.106613,641.4__,__-35.277692,149.106694,640.3__,__-35.277679,149.106707,640.1__,__-35.277759,149.106908,638.0__,__-35.277901,149.10707,635.7__,__-35.278048,149.107202,633.5__,__-35.278231,149.107309,630.9__,__-35.278416,149.107343,628.6__,__-35.278607,149.107287,626.2__,__-35.278712,149.10725,624.8__,__-35.278897,149.107175,622.7__,__-35.279087,149.107112,620.5__,__-35.279268,149.107079,618.1__,__-35.279461,149.107104,615.3__,__-35.279648,149.107168,612.6__,__-35.279818,149.107279,610.0__,__-35.279965,149.107412,607.6__,__-35.280096,149.107593,604.7__,__-35.280188,149.107797,601.9__,__-35.28021,149.108037,599.0__,__-35.280219,149.108167,597.5__,__-35.280191,149.108392,595.0__,__-35.280154,149.108618,592.1__,__-35.280109,149.108856,588.8__,__-35.280085,149.109082,585.4__,__-35.280073,149.109321,581.6__,__-35.280118,149.109532,578.7__,__-35.280147,149.109577,578.0__,__-35.280157,149.109573,577.3__,__-35.280199,149.109698,576.2__,__-35.280257,149.109822,575.1__,__-35.280312,149.110044,573.2__,__-35.280303,149.110194,571.6__,__-35.280293,149.110411,570.7__,__-35.28017,149.1106,572.7__,__-35.279999,149.11073,574.6__,__-35.279835,149.110846,575.7__,__-35.279674,149.110988,574.2__,__-35.279531,149.111131,573.0__,__-35.27938,149.111271,574.6__,__-35.279223,149.111389,576.1__,__-35.27909,149.111505,577.6__,__-35.278943,149.111646,578.5__,__-35.278802,149.111784,577.3__,__-35.278658,149.111923,576.9__,__-35.278498,149.112058,576.5__,__-35.278329,149.112199,576.1__,__-35.278164,149.112347,575.6__,__-35.278013,149.11248,575.2__,__-35.277867,149.11261,574.7__,__-35.277705,149.112752,573.7__,__-35.277574,149.112925,571.0__,__-35.277681,149.113119,570.9__,__-35.277816,149.113283,570.7__,__-35.277933,149.113477,570.3__,__-35.27798,149.113569,570.1__,__-35.278045,149.113821,569.9__,__-35.278099,149.11394,569.9__,__-35.278161,149.114168,569.7__,__-35.278178,149.114296,569.6__,__-35.278169,149.114438,569.4__,__-35.278133,149.114684,569.1__,__-35.278195,149.114894,567.6__,__-35.278374,149.11498,566.1__,__-35.278469,149.115022,565.0__,__-35.278664,149.115116,565.0__,__-35.278812,149.11522,565.0__,__-35.278811,149.115206,565.0__,__-35.278832,149.115181,565.0__,__-35.278887,149.115132,565.0__,__-35.279015,149.115086,565.0__,__-35.279041,149.115152,564.9__,__-35.279005,149.115233,564.6__,__-35.278976,149.115292,564.4__,__-35.278963,149.115324,564.3__,__-35.278937,149.115416,564.0__,__-35.278937,149.115647,563.4__,__-35.278957,149.115777,563.0__,__-35.278976,149.115882,562.6__,__-35.278999,149.116011,562.1__,__-35.279,149.116045,562.0__,__-35.278994,149.116115,562.0__,__-35.278988,149.116151,562.0__,__-35.278953,149.116399,562.0__,__-35.278945,149.11664,562.2__,__-35.278949,149.116677,562.4__,__-35.278983,149.11691,563.0__,__-35.279028,149.11713,563.2__,__-35.279071,149.117375,563.4__,__-35.279109,149.117613,563.7__,__-35.279107,149.117861,564.6__,__-35.279149,149.118092,567.4__,__-35.279219,149.118308,568.5__,__-35.279348,149.118493,568.0__,__-35.279463,149.118658,568.0__,__-35.27958,149.118859,568.0__,__-35.279663,149.118995,568.0__,__-35.279786,149.11919,568.0__,__-35.279885,149.119365,568.4__,__-35.279858,149.119419,568.5__,__-35.279719,149.119536,568.9__,__-35.279561,149.119677,569.3__,__-35.279392,149.119825,569.5__,__-35.279241,149.119972,569.4__,__-35.279227,149.119993,569.4__,__-35.279218,149.12002,569.4__,__-35.279219,149.120081,569.3__,__-35.27924,149.120166,569.3__,__-35.279244,149.120197,569.2__,__-35.279261,149.120427,570.1__,__-35.279237,149.120539,569.7__,__-35.279273,149.120758,570.1__,__-35.279348,149.120972,570.0__,__-35.279457,149.12117,570.0__,__-35.279449,149.121202,570.0__,__-35.279304,149.121354,570.0__,__-35.279159,149.121464,570.0__,__-35.279136,149.121488,570.0__,__-35.279106,149.121555,570.0__,__-35.279041,149.121714,570.0__,__-35.279003,149.121851,570.0__,__-35.278972,149.121985,570.0__,__-35.278886,149.12216,570.0__,__-35.278813,149.122328,570.0__,__-35.278724,149.122564,570.7__,__-35.278713,149.122605,571.3__,__-35.27867,149.122768,572.1__,__-35.278604,149.122998,571.8__,__-35.278572,149.123057,571.8__,__-35.278547,149.123158,571.8__,__-35.278662,149.123359,571.9__,__-35.278771,149.123525,571.9__,__-35.278841,149.123763,572.0__,__-35.278832,149.123798,571.8__,__-35.278703,149.123938,571.6__,__-35.278553,149.124088,571.3__,__-35.278492,149.124129,571.2__,__-35.278393,149.12419,571.0__,__-35.278226,149.124314,570.7__,__-35.278088,149.124462,570.5__,__-35.277949,149.124606,570.3__,__-35.277799,149.12473,570.0__,__-35.27767,149.124797,569.9__,__-35.277565,149.124839,569.7__,__-35.27751,149.124865,569.6__,__-35.277461,149.124894,569.6__,__-35.277428,149.125142,569.3__,__-35.277405,149.125192,569.2__,__-35.277241,149.125347,569.0__,__-35.277071,149.125409,569.2__,__-35.27692,149.125395,569.3__,__-35.276891,149.125399,569.2__,__-35.276877,149.125401,569.2";

    public abstract WalkDao walkDao();

    private static WalkRoomDatabase INSTANCE;

    static WalkRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (WalkRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WalkRoomDatabase.class, "walk_database")
                            // TODO: Line below adds test database, remove on final
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    // TODO: Temporary file to add stuff to database
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WalkDao mDao;

        PopulateDbAsync(WalkRoomDatabase db) {
            mDao = db.walkDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Walk id = new Walk(1, "Robs Walk", "Long Walk__,__Hard Difficulty__,__Long Walk__,__Hard Difficulty__,__Long Walk",
                    4, 5.00,"1:05", "this is Robs walk",temp_route);
            mDao.insert(id);
            id = new Walk(2, "Harrys Walk","Short Walk__,__Easy Difficulty",
                    2, 17.75,"0:42", "this is Harrys walk","");
            mDao.insert(id);

            new Thread(new Runnable(){
                public void run(){
                    //open socket
                    try {
                        String host = "35.197.184.151";
                        int port = 10003;
                        Socket sock = new Socket(host, port);
                        DataInputStream in = new DataInputStream(sock.getInputStream());
                        String msg = in.readUTF();
                        Gson gson = new Gson();

                        // con is the received Contact class
                        Walk con = gson.fromJson(msg, Walk.class);
                        // For Test
                        Log.d("walkRecieve", con.mName);
                        mDao.insert(con);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return null;
        }
    }




}
