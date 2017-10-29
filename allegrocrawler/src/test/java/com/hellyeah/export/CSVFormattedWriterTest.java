package com.hellyeah.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.hellyeah.model.Auction;


public class CSVFormattedWriterTest {

	private static final String CSV_DIR = "c:\\Development\\projects\\hackyeah\\";

	private CSVFormattedWriter writer;

	@Before
	public void setUp() {
		writer = new CSVFormattedWriter();
		writer.setHeader("#nick;nip;email;phone,,");

		CSVWriter csvWriter = new CSVWriter();
		csvWriter.setDirectory(CSV_DIR);
		writer.setWriter(csvWriter);
	}

	@Test
	public void write() throws Exception {
		// given
		final List<Auction> auctions = Arrays.asList(
				new Auction().withNickname("smart_fon").withnIP("8992746964").withEmail("smartfon.allegro@gmail.com").withPhone("535468930").withPhone("888599879").withPhone("608092396"),
				new Auction().withNickname("KUBTEL_S").withEmail("meandmobileallegro@gmail.com").withPhone("509919099").withPhone("530222477"),
				new Auction().withNickname("MDM-KOM").withnIP("7272801172").withPhone("502928871"),
				new Auction().withNickname("www_PL-GSM_pl").withnIP("727-280-11-72")
		);

		// when
		writer.writeAll(auctions);

		// then
		//TODO make mock for CSVWriter and check write() method call
	}

	@Test
	public void writeFromString() throws Exception {
		// given
		String stringAuctions = "[Auction{nickname='all_elektrocity', nIP='646-283-13-07', email='allegro@elektrocity.eu', phones=[608 092 396]}, Auction{nickname='www_PL-GSM_pl', nIP='895-203-14-14', email='sklep@pl-gsm.pl', phones=[]}, Auction{nickname='all_elektrocity', nIP='646-283-13-07', email='allegro@elektrocity.eu', phones=[608 092 396]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='www_PL-GSM_pl', nIP='895-203-14-14', email='sklep@pl-gsm.pl', phones=[]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='MULTISERW_RENOMA', nIP='8992794874', email='multiserwis_ch_renoma@interia.pl', phones=[533 25 45 45]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='all_elektrocity', nIP='646-283-13-07', email='allegro@elektrocity.eu', phones=[608 092 396]}, Auction{nickname='all_elektrocity', nIP='646-283-13-07', email='allegro@elektrocity.eu', phones=[608 092 396]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='all_elektrocity', nIP='646-283-13-07', email='allegro@elektrocity.eu', phones=[608 092 396]}, Auction{nickname='all_elektrocity', nIP='646-283-13-07', email='allegro@elektrocity.eu', phones=[608 092 396]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='MULTISERW_RENOMA', nIP='8992794874', email='multiserwis_ch_renoma@interia.pl', phones=[533 25 45 45]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='AKsklepGSM', nIP='6831919269', email='bejooaw@gmail.com', phones=[796-939-594]}, Auction{nickname='wroctel', nIP='', email='wroctel@gmail.com', phones=[507 250 780]}, Auction{nickname='wroctel', nIP='', email='wroctel@gmail.com', phones=[507 250 780]}, Auction{nickname='multiserwis_tele', nIP='8992794874', email='new.brands.teletorium@gmail.com', phones=[518278491]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='___PeTEL___', nIP='729-270-11-89', email='lodz@petel.pl', phones=[881-389-280, 607-045-099]}, Auction{nickname='___PeTEL___', nIP='729-270-11-89', email='lodz@petel.pl', phones=[881-389-280, 607-045-099]}, Auction{nickname='Lombard-Beta-1', nIP='', email='lombard.beta.gryfice@wp.pl', phones=[601273669]}, Auction{nickname='Ascom-CSW', nIP='559-145-55-21', email='szymonsobiczewski@gmail.com', phones=[]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='www_PL-GSM_pl', nIP='895-203-14-14', email='sklep@pl-gsm.pl', phones=[]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='www_PL-GSM_pl', nIP='895-203-14-14', email='sklep@pl-gsm.pl', phones=[]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='MULTISERW_RENOMA', nIP='8992794874', email='multiserwis_ch_renoma@interia.pl', phones=[533 25 45 45]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='MULTISERW_RENOMA', nIP='8992794874', email='multiserwis_ch_renoma@interia.pl', phones=[533 25 45 45]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='MULTISERW_RENOMA', nIP='8992794874', email='multiserwis_ch_renoma@interia.pl', phones=[533 25 45 45]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='GOLD_GAMES2', nIP='6991861840', email='goldgames2@interia.pl', phones=[694 550 450, 515 302 304]}, Auction{nickname='sebastian34311', nIP='769-217-86-91', email='mdkom.biuro@gmail.com', phones=[577-755-014]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='mrpartner', nIP='', email='', phones=[]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='i4Less-Mobiles', nIP='959-076-29-67', email='pomoc@i4less.pl', phones=[+48 888 818 188]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='wieslaw017', nIP='667-116-44-01', email='allegro.wieslaw017@wp.pl', phones=[695 865 574]}, Auction{nickname='Free_For_All', nIP='10751075', email='', phones=[534610993]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='MULTISERW_RENOMA', nIP='8992794874', email='multiserwis_ch_renoma@interia.pl', phones=[533 25 45 45]}, Auction{nickname='www_PL-GSM_pl', nIP='895-203-14-14', email='sklep@pl-gsm.pl', phones=[]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='aleKomp', nIP='959-184-21-02', email='', phones=[796282814]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='Free_For_All', nIP='10751075', email='', phones=[534610993]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='www_PL-GSM_pl', nIP='895-203-14-14', email='sklep@pl-gsm.pl', phones=[]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='smart_fon', nIP='899-274-69-64', email='smartfon.allegro@gmail.com', phones=[535-468-930, 888-599-879]}, Auction{nickname='MULTISERW_RENOMA', nIP='8992794874', email='multiserwis_ch_renoma@interia.pl', phones=[533 25 45 45]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='mrpartner', nIP='', email='', phones=[]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='YALABALALA', nIP='', email='bestallegro3@gmail.com', phones=[507242040]}, Auction{nickname='PhonesEurope', nIP='', email='import.sklep1@gmail.com', phones=[690163715]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='KUBTEL_S', nIP='', email='meandmobileallegro@gmail.com', phones=[509 919 099, 530 222 477]}, Auction{nickname='KMKI_PL', nIP='676-245-26-66', email='kmki.pl@gmail.com', phones=[600-723-713, 600-117-118]}, Auction{nickname='GOLD_GAMES2', nIP='6991861840', email='goldgames2@interia.pl', phones=[694 550 450, 515 302 304]}, Auction{nickname='MULTISERW_RENOMA', nIP='8992794874', email='multiserwis_ch_renoma@interia.pl', phones=[533 25 45 45]}, Auction{nickname='cell_world', nIP='899-274-69-64', email='cell_world@onet.eu', phones=[786 249 686]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='gsmwroc', nIP='899-274-69-64', email='wrocgsm@gmail.com', phones=[507-250-780]}, Auction{nickname='MDM-KOM', nIP='727-280-11-72', email='mdmkom@gmail.com', phones=[502 928 871]}, Auction{nickname='phonelodz', nIP='', email='phonelodz@gmail.com', phones=[530018350, 533280050]}]";
		final List<Auction> auctions = prepareAuction(stringAuctions);

		// when
		writer.writeAll(auctions);

		// then
		//TODO make mock for CSVWriter and check write() method call
	}

	private static final Pattern AUCTIONS_PATTERN = Pattern.compile("Auction\\{nickname='([^']+)', nIP='([^']+)', email='([^']+)', phones=\\[([^\\]]+)\\]}");

	private List<Auction> prepareAuction(String stringAuctions) {
		List<Auction> auctions = new ArrayList<>();

		Matcher m = AUCTIONS_PATTERN.matcher(stringAuctions);
		while (m.find()) {
			auctions.add(new Auction().withNickname(m.group(1)).withnIP(m.group(2)).withEmail(m.group(3)).withPhones(getPhones(m.group(4))));
		}

		return auctions;
	}

	private List<String> getPhones(String phones) {
		return Arrays.asList(phones.split(","));
	}

}