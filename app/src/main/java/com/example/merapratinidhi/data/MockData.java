package com.example.merapratinidhi.data;

import com.example.merapratinidhi.models.AssemblyConstituency;
import com.example.merapratinidhi.models.ParliamentaryConstituency;
import com.example.merapratinidhi.models.Representative;
import com.example.merapratinidhi.models.Ward;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static List<Representative> getRepresentatives() {
        List<Representative> list = new ArrayList<>();

        // COUNCILLORS (10)
        list.add(new Representative(
                "rep_1", "Ravi Hans", "BJP", "Councillor",
                "Ward 61 — Kohat Enclave", "+91 98100 12345",
                "ravi.hans@mcd.delhi.gov.in", "MCD Ward Office, Sector 8, Rohini, Delhi 110085",
                "Delhi State Election Commission", "September 2026", "RH", "#E65100"));

        list.add(new Representative(
                "rep_2", "Sunita Rani", "AAP", "Councillor",
                "Ward 128 — Dichaon Kalan", "+91 98111 23456",
                "sunita.rani@mcd.delhi.gov.in", "MCD Ward Office, Najafgarh Zone, Delhi 110043",
                "Delhi State Election Commission", "September 2026", "SR", "#00796B"));

        list.add(new Representative(
                "rep_3", "Amit Sharma", "INC", "Councillor",
                "Ward 201 — Lalita Park", "+91 98122 34567",
                "amit.sharma@mcd.delhi.gov.in", "MCD Ward Office, Laxmi Nagar, Delhi 110092",
                "Delhi State Election Commission", "August 2026", "AS", "#1565C0"));

        list.add(new Representative(
                "rep_4", "Rekha Gupta", "BJP", "Councillor",
                "Ward 55 — Shalimar Bagh North", "+91 98133 45678",
                "rekha.gupta@mcd.delhi.gov.in", "Community Centre, Block CA, Shalimar Bagh, Delhi 110088",
                "Delhi State Election Commission", "September 2026", "RG", "#E65100"));

        list.add(new Representative(
                "rep_5", "Mukesh Goel", "AAP", "Councillor",
                "Ward 72 — Model Town", "+91 98144 56789",
                "mukesh.goel@mcd.delhi.gov.in", "Zone Office, Civil Lines, Delhi 110054",
                "Delhi State Election Commission", "August 2026", "MG", "#00796B"));

        list.add(new Representative(
                "rep_6", "Priyanka Gautam", "AAP", "Councillor",
                "Ward 225 — Seelampur", "+91 98155 67890",
                "priyanka.gautam@mcd.delhi.gov.in", "MCD Complex, Yamuna Vihar, Delhi 110053",
                "Delhi State Election Commission", "September 2026", "PG", "#00796B"));

        list.add(new Representative(
                "rep_7", "Joginder Singh", "BJP", "Councillor",
                "Ward 247 — Sadatpur", "+91 98166 78901",
                "joginder.singh@mcd.delhi.gov.in", "Ward Office, Karawal Nagar, Delhi 110094",
                "Delhi State Election Commission", "August 2026", "JS", "#E65100"));

        list.add(new Representative(
                "rep_8", "Vandana Kumari", "AAP", "Councillor",
                "Ward 62 — Pitampura", "+91 98177 89012",
                "vandana.kumari@mcd.delhi.gov.in", "MCD Complex, ND Block, Pitampura, Delhi 110034",
                "Delhi State Election Commission", "September 2026", "VK", "#00796B"));

        list.add(new Representative(
                "rep_9", "Rajesh Kumar", "BJP", "Councillor",
                "Ward 142 — Patel Nagar", "+91 98188 90123",
                "rajesh.kumar@mcd.delhi.gov.in", "West Zone MCD Office, Rajouri Garden, Delhi 110027",
                "Delhi State Election Commission", "July 2026", "RK", "#E65100"));

        list.add(new Representative(
                "rep_10", "Sangeeta Malhotra", "INC", "Councillor",
                "Ward 88 — Civil Lines", "+91 98199 01234",
                "sangeeta.m@mcd.delhi.gov.in", "MCD Zonal Building, Rajpur Road, Delhi 110054",
                "Delhi State Election Commission", "September 2026", "SM", "#1565C0"));


        // MLAs (5)
        list.add(new Representative(
                "rep_11", "Bandana Kumari", "AAP", "MLA",
                "Shalimar Bagh Assembly", "+91 98711 00112",
                "mla.shalimarbagh@delhi.gov.in", "Delhi Legislative Assembly, Old Secretariat, Delhi 110054",
                "Chief Electoral Officer Delhi", "September 2026", "BK", "#00796B"));

        list.add(new Representative(
                "rep_12", "Vijender Gupta", "BJP", "MLA",
                "Rohini Assembly", "+91 98711 22334",
                "mla.rohini@delhi.gov.in", "MLA Office, Pocket D-14, Sector 7, Rohini, Delhi 110085",
                "Chief Electoral Officer Delhi", "September 2026", "VG", "#E65100"));

        list.add(new Representative(
                "rep_13", "Akhilesh Pati Tripathi", "AAP", "MLA",
                "Model Town Assembly", "+91 98711 33445",
                "mla.modeltown@delhi.gov.in", "MLA Office, Hudson Lane, GTB Nagar, Delhi 110009",
                "Chief Electoral Officer Delhi", "August 2026", "AT", "#00796B"));

        list.add(new Representative(
                "rep_14", "Raaj Kumar Anand", "BJP", "MLA",
                "Patel Nagar Assembly", "+91 98711 44556",
                "mla.patelnagar@delhi.gov.in", "MLA Office, Main Patel Road, West Delhi 110008",
                "Chief Electoral Officer Delhi", "September 2026", "RA", "#E65100"));

        list.add(new Representative(
                "rep_15", "Imran Hussain", "AAP", "MLA",
                "Ballimaran Assembly", "+91 98711 55667",
                "mla.ballimaran@delhi.gov.in", "MLA Office, Chandni Chowk, Delhi 110006",
                "Chief Electoral Officer Delhi", "August 2026", "IH", "#00796B"));


        // MPs (3)
        list.add(new Representative(
                "rep_16", "Yogender Chandolia", "BJP", "MP",
                "North West Delhi (SC)", "+91 99100 11223",
                "mp.northwestdelhi@sansad.in", "MP Office, Sector 15, Rohini, New Delhi 110089",
                "Election Commission of India", "September 2026", "YC", "#E65100"));

        list.add(new Representative(
                "rep_17", "Manoj Tiwari", "BJP", "MP",
                "North East Delhi", "+91 99100 22334",
                "mp.northeastdelhi@sansad.in", "MP Office, C-Block, Yamuna Vihar, Delhi 110053",
                "Election Commission of India", "September 2026", "MT", "#E65100"));

        list.add(new Representative(
                "rep_18", "Harsh Malhotra", "BJP", "MP",
                "East Delhi", "+91 99100 33445",
                "mp.eastdelhi@sansad.in", "MP Office, Swasthya Vihar, Preet Vihar, Delhi 110092",
                "Election Commission of India", "August 2026", "HM", "#E65100"));

        return list;
    }

    public static List<Ward> getWards() {
        List<Ward> list = new ArrayList<>();
        list.add(new Ward("ward_61", 61, "Kohat Enclave", "Ravi Hans", "Shalimar Bagh", "North West Delhi"));
        list.add(new Ward("ward_128", 128, "Dichaon Kalan", "Sunita Rani", "Najafgarh", "West Delhi"));
        list.add(new Ward("ward_201", 201, "Lalita Park", "Amit Sharma", "Laxmi Nagar", "East Delhi"));
        list.add(new Ward("ward_55", 55, "Shalimar Bagh North", "Rekha Gupta", "Shalimar Bagh", "North West Delhi"));
        list.add(new Ward("ward_72", 72, "Model Town", "Mukesh Goel", "Model Town", "Chandni Chowk"));
        list.add(new Ward("ward_225", 225, "Seelampur", "Priyanka Gautam", "Seelampur", "North East Delhi"));
        list.add(new Ward("ward_247", 247, "Sadatpur", "Joginder Singh", "Karawal Nagar", "North East Delhi"));
        list.add(new Ward("ward_62", 62, "Pitampura", "Vandana Kumari", "Shalimar Bagh", "North West Delhi"));
        list.add(new Ward("ward_142", 142, "Patel Nagar", "Rajesh Kumar", "Patel Nagar", "New Delhi"));
        list.add(new Ward("ward_88", 88, "Civil Lines", "Sangeeta Malhotra", "Model Town", "Chandni Chowk"));
        return list;
    }

    public static List<AssemblyConstituency> getAssemblyConstituencies() {
        List<AssemblyConstituency> list = new ArrayList<>();
        list.add(new AssemblyConstituency("ac_1", "Shalimar Bagh", "Bandana Kumari", "North West Delhi", 4));
        list.add(new AssemblyConstituency("ac_2", "Rohini", "Vijender Gupta", "North West Delhi", 4));
        list.add(new AssemblyConstituency("ac_3", "Model Town", "Akhilesh Pati Tripathi", "Chandni Chowk", 4));
        list.add(new AssemblyConstituency("ac_4", "Patel Nagar", "Raaj Kumar Anand", "New Delhi", 4));
        list.add(new AssemblyConstituency("ac_5", "Ballimaran", "Imran Hussain", "Chandni Chowk", 4));
        return list;
    }

    public static List<ParliamentaryConstituency> getParliamentaryConstituencies() {
        List<ParliamentaryConstituency> list = new ArrayList<>();
        list.add(new ParliamentaryConstituency("pc_1", "North West Delhi", "Yogender Chandolia", 10));
        list.add(new ParliamentaryConstituency("pc_2", "North East Delhi", "Manoj Tiwari", 10));
        list.add(new ParliamentaryConstituency("pc_3", "East Delhi", "Harsh Malhotra", 10));
        return list;
    }
}
