package jp.co.example.ecommerce_d.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品追加で使用するフォームです.
 * 
 * @author kazuteru.takahashi
 *
 */
public class RegisterItemForm {
	/** 商品名 */
	@NotBlank(message = "商品名を入力してください")
	private String name;
	/** 商品説明 */
	@NotBlank(message = "商品説明を入力してください")
	private String description;
	/** Mサイズの価格 */
	@NotBlank(message = "Mサイズの料金を入力してください")
	@Range(min = 0,message = "0円以上で入力してください")
	private String priceM;
	/** Lサイズの */
	@NotBlank(message = "Lサイズの料金を入力してください")
	@Range(min = 0,message = "0円以上で入力してください")
	private String priceL;
	/** 画像ファイル */
	private MultipartFile imagePath;

	@Override
	public String toString() {
		return "RegisterItemForm [name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", imagePath=" + imagePath + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriceM() {
		return priceM;
	}

	public void setPriceM(String priceM) {
		this.priceM = priceM;
	}

	public String getPriceL() {
		return priceL;
	}

	public void setPriceL(String priceL) {
		this.priceL = priceL;
	}

	public MultipartFile getImagePath() {
		return imagePath;
	}

	public void setImagePath(MultipartFile imagePath) {
		this.imagePath = imagePath;
	}

}
