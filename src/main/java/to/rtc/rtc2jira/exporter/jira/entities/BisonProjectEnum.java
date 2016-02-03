package to.rtc.rtc2jira.exporter.jira.entities;

import java.util.EnumSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import to.rtc.rtc2jira.ExportManager;

public enum BisonProjectEnum {

  UNSET("projektname.literal.l105", "10251") {
    @Override
    public CustomFieldOption getCustomFieldOption() {
      return CustomFieldOption.NONE;
    }
  }, //
  Ametras_Moebel_Prototyp("projektname.literal.l119", "10206"), // x
  App_Handelsvertreter("projektname.literal.l127", "10215"), // x
  B4AGROLA_Betreuung("projektname.literal.l108", "10216"), // x
  B4GOF_Trading("projektname.literal.l25", "10217"), // x
  B4LANDI("projektname.literal.l4", "10218"), // x
  B4LAP_Betreuung("projektname.literal.l31", "10219"), // x
  B4LCH_Betreuung("projektname.literal.l77", "10220"), // x
  B4UFA("projektname.literal.l61", "10221"), // x
  B4PFB_Pflanzenschutz("projektname.literal.l92", "10222"), // x
  Bison_ITS_Betreuung("projektname.literal.l55", "10223"), // x
  B4BGroup_Systemfusion("projektname.literal.l123", "10224"), // x
  EAG_Betreuung("projektname.literal.l102", "10225"), // x
  ERP_Feldsaaten_Freudenberger("projektname.literal.l120", "10226"), // x
  Hoyer_ERP_Gesamtlösung("projektname.literal.l112", "10227"), // x
  InoTex_Betreuung("projektname.literal.l32", "10228"), // x
  KönigStahl_Warschau("projektname.literal.l28", "10229"), // x
  RUAG_Environment("projektname.literal.l121", "10230"), // x
  Uvavins("projektname.literal.l116", "10231"), // x
  PROD_Performance_DL("projektname.literal.l124", "10232"), // x
  PROD_Split_DL("projektname.literal.l125", "10233"), // x
  Backlog_Agrar_EL("projektname.literal.l115", "10234"), // x
  Backlog_Multi_Branch_EL("projektname.literal.l96", "10235"), // x
  BL_GH_IDM("projektname.literal.l130", "10236"), // x
  Backlog_Finance_EL("projektname.literal.l93", "10237"), // x
  Backlog_Retail_EL("projektname.literal.l99", "10238"), // x
  Backlog_Steel_EL("projektname.literal.l97", "10239"), // x
  Backlog_Technology_EL("projektname.literal.l95", "10240"), // x
  Backlog_Unterhalt_und_Optimierungen_2014("projektname.literal.l98", "10241"), // x
  Backlog_Printing("projektname.literal.l126", "10242"), // x
  Backlog_Mobile_Strategie("projektname.literal.l131", "10243"), // x
  PROD_NewCore("projektname.literal.l122", "10244"), // x
  Retail_Bison_ESL("projektname.literal.l132", "10245"), // x
  Reservationen_Neu_Projekte("projektname.literal.l129", "10246"), // x
  REL_GK_Wartung("projektname.literal.l75", "10247"), // x
  REL_Architektur("projektname.literal.l33", "10248"), // x
  REL_Cross("projektname.literal.l34", "10249"), // x
  REL_Maintenance("projektname.literal.l53", "10250"), // x
  B4AGROLA("projektname.literal.l2", "10252"), // x
  DAKODA_Branchenmodell_Fertigung("projektname.literal.l38", "10254"), // x
  PROD_ELearning_Aufbau_Betrieb_2013("projektname.literal.l80", "10254"), // x
  PROD_Safira_Ergonomie("projektname.literal.l26", "10255"), // x
  Frigemo_Handelsfirmen("projektname.literal.l101", "10256"), // x
  RTC_SCM_at_Bison("projektname.literal.l56", "10257"), // x
  PROD_PQ_Cockpit("projektname.literal.l83", "10258"), // x
  PROD_MilestoneRelease_2012("projektname.literal.l30", "10259"), // x
  B4LCH_Phase_3("projektname.literal.l29", "10260"), // x
  PROD_Base4Bison("projektname.literal.l42", "10261"), // x
  MOD_BRA_Intersect("projektname.literal.l41", "10262"), // x
  App_Handelsvertreter_Abrechnung_BiMX("projektname.literal.l127", "10263"), // x
  MOD_BRA_BDF("projektname.literal.l35", "10264"), // x
  PROD_Feature4Bison("projektname.literal.l43", "10265"), // x
  PROD_Evolution_II("projektname.literal.l24", "10266"), // x
  Gero_Hoberg_Driesch("projektname.literal.l60", "10267"), // x
  MOD_BRA_Basisentwicklung("projektname.literal.l8", "10268"), // x
  Partner_und_Sales_Projekte("projektname.literal.l78", "10269"), // x
  PROD_Modularisierung("projektname.literal.l40", "10270"), // x
  MOD_BRA_Nemo_Finanzintegration("projektname.literal.l22", "10271"), // x
  Andréfleurs("projektname.literal.l113", "10272"), // x
  REL_Scrum_Team_Arbeiten_Altlasten("projektname.literal.l52", "10273"), // x
  B4KOWAG("projektname.literal.l94", "10274"), // x
  PROD_Performance("projektname.literal.l89", "10275"), // x
  MOD_BRA_Aufbau_Retail("projektname.literal.l16", "10276"), // x
  DRWZ_Phase_III("projektname.literal.l65", "10277"), // x
  MOD_BRA_CONSOLVO_WE_2("projektname.literal.l21", "10278"), // x
  BM_10_Aufbau_Standardmandant_Stahl("projektname.literal.l67", "10279"), // x
  MOD_BRA_Zinal_Produktion("projektname.literal.l20", "10280"), // x
  Bison_Technology_DL("projektname.literal.l100", "10281"), // x
  MOD_BRA_SAM4BITS("projektname.literal.l10", "10282"), // x
  BEST_Performance("projektname.literal.l48", "10283"), // x
  B4BisonGroup_Erweiterungen_2013("projektname.literal.l103", "10284"), // x
  PROD_Optimierung_Testumgebung_2013("projektname.literal.l81", "10285"), // x
  B4LCH_Stock_Optimizer("projektname.literal.l114", "10286"), // x
  EL_TGH_Multibranche("projektname.literal.l72", "10287"), // x
  Backlog_DEVL_EL("projektname.literal.l109", "10288"), // x
  PROD_High_Availability("projektname.literal.l59", "10289"), // x
  Standard_Development("projektname.literal.l12", "10290"), // x
  MOD_BRA_Zunder_Branchen_Stahl("projektname.literal.l23", "10291"), // x
  PROD_Testdatenbasis("projektname.literal.l37", "10292"), // x
  PROD_Easy_Quattro("projektname.literal.l51", "10293"), // x
  Enterprise_SITE("projektname.literal.l88", "10294"), // x
  B4LAP_Sortieranlage("projektname.literal.l76", "10295"), // x
  MOD_BRA_Seriennummer("projektname.literal.l57", "10296"), // x
  Pilatus_Quattro_Restanzen("projektname.literal.l62", "10297"), // x
  MOD_BRA_Apollon_Finanz("projektname.literal.l17", "10298"), // x
  PROD_Mobile_Applications("projektname.literal.l106", "10299"), // x
  B4LAP_EDI("projektname.literal.l85", "10300"), // x
  B4LAP_Phase_2("projektname.literal.l6", "10301"), // x
  PROD_Symphony4Bison("projektname.literal.l44", "10302"), // x
  PQ_Testabdeckung_Rest_Pilatus("projektname.literal.l36", "10303"), // x
  PROD_OotB("projektname.literal.l18", "10304"), // x
  PROD_Performance_2013("projektname.literal.l79", "10305"), // x
  PROD_Speed("projektname.literal.l13", "10306"), // x
  BEST_PQ_Maintenance("projektname.literal.l107", "10307"), // x
  FLaGschiff("projektname.literal.l134", "10308"), // x

  Systemunterhalt_Build_Test_Bereitstellung("projektname.literal.l17.mapped", "10405"), //
  Proj20("projektname.literal.l28.mapped", "10406"), //
  Last_Test("projektname.literal.l16.mapped", "10407"), //
  Proj19("projektname.literal.l27.mapped", "10408"), //
  Kurse_eLearning("projektname.literal.l19.mapped", "10409"), //
  Redaktion("projektname.literal.l18.mapped", "10410"), //
  Performance_Issues_Kunden_Intern("projektname.literal.l2.mapped", "10411"), //
  LAB_Manager("projektname.literal.l6.mapped", "10412"), //
  Performance_Test("projektname.literal.l13.mapped", "10413"), //
  Proj16("projektname.literal.l24.mapped", "10414"), //
  Projektname_auswählen("projektname.literal.l12.mapped", "10251"), // use SELECT from other list
  Proj15("projektname.literal.l23.mapped", "10416"), //
  Swisscom_DMC("projektname.literal.l4.mapped", "10417"), //
  Systemvorgaben_Dokumentationen("projektname.literal.l15.mapped", "10418"), //
  Proj18("projektname.literal.l26.mapped", "10419"), //
  Proj17("projektname.literal.l25.mapped", "10420"), //
  Testautomatisierung("projektname.literal.l20.mapped", "10421"), //
  Sintra("projektname.literal.l8.mapped", "10422"), //
  Tools("projektname.literal.l22.mapped", "10423"), //
  Plattform_Test("projektname.literal.l10.mapped", "10424"), //
  Coaching_Teststabilität("projektname.literal.l21.mapped", "10425"); //

  private String rctId;
  private String jiraId;
  static private final Logger LOGGER = Logger.getLogger(BisonProjectEnum.class.getName());
  static {
    LOGGER.addHandler(ExportManager.DEFAULT_LOG_HANDLER);
  }

  private BisonProjectEnum(String rctId, String jiraId) {
    this.rctId = rctId;
    this.jiraId = jiraId;
  }

  public String getRctId() {
    return rctId;
  }

  public void setRctId(String rctId) {
    this.rctId = rctId;
  }

  public String getJiraId() {
    return jiraId;
  }

  public void setJiraId(String jiraId) {
    this.jiraId = jiraId;
  }

  public CustomFieldOption getCustomFieldOption() {
    return new CustomFieldOption(getJiraId());
  }

  public static final Optional<BisonProjectEnum> forJiraId(String jiraId) {
    EnumSet<BisonProjectEnum> all = EnumSet.allOf(BisonProjectEnum.class);
    Optional<BisonProjectEnum> first = all.stream().filter(item -> item.getJiraId().equals(jiraId)).findFirst();
    if (!first.isPresent()) {
      LOGGER.log(Level.SEVERE, "Could not find a BisonProjectEnum entry for the jira id " + jiraId);
    }
    return first;
  }

  public static final Optional<BisonProjectEnum> forRtcId(String rtcId) {
    EnumSet<BisonProjectEnum> all = EnumSet.allOf(BisonProjectEnum.class);
    Optional<BisonProjectEnum> first = all.stream().filter(item -> item.getRctId().equals(rtcId)).findFirst();
    if (!first.isPresent()) {
      LOGGER.log(Level.SEVERE, "Could not find a BisonProjectEnum entry for the rtc id " + rtcId);
    }
    return first;
  }


}
