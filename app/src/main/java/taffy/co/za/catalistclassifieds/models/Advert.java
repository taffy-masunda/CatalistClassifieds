package taffy.co.za.catalistclassifieds.models;

import java.util.Date;


public class Advert {


    /* Default constructor used to serialization */
    public Advert() {
    }


    public Advert(String advert_id, String placed_date, String title, String category, String description, String price, String institution, String campus, String name, String email, String phone, String whatsapp) {
        this.advert_id = advert_id;
        this.title = title;
        this.placed_date = placed_date;
        this.category = category;
        this.description = description;
        this.price = price;
        this.institution = institution;
        this.campus = campus;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.whatsapp = whatsapp;
    }

    private String advert_id;

    public String getAdvertId() { return this.advert_id; }

    public void setAdvertId(String advert_id) { this.advert_id = advert_id; }

    private String placed_date;

    public String getPlacedDate() { return this.placed_date; }

    public void setPlacedDate(String placed_date) { this.placed_date = placed_date; }

    private String title;

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    private String category;

    public String getCategory() { return this.category; }

    public void setCategory(String category) { this.category = category; }

    private String description;

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    private String price;

    public String getPrice() { return this.price; }

    public void setPrice(String price) { this.price = price; }

    private String institution;

    public String getInstitution() { return this.institution; }

    public void setInstitution(String institution) { this.institution = institution; }

    private String campus;

    public String getCampus() { return this.campus; }

    public void setCampus(String campus) { this.campus = campus; }

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String email;

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    private String phone;

    public String getPhone() { return this.phone; }

    public void setPhone(String phone) { this.phone = phone; }

    private String whatsapp;

    public String getWhatsapp() { return this.whatsapp; }

    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }

    private String image_name;

    public String getImageName() { return this.image_name; }

    public void setImageName(String image_name) { this.image_name = image_name; }

}
