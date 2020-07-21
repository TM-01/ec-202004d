package jp.co.example.ecommerce_d.domain;

/**
 * トッピングを表すドメイン.
 * 
 * @author yu.konishi
 *
 */
public class Topping {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 名前
	 */
	private String name;
	/**
	 * Mサイズの値段
	 */
	private Integer priceM;
	/**
	 * Lサイズの値段
	 */
	private Integer priceL;
	/**
	 * 削除フラグ
	 */
	private Boolean deleted;

	public Topping() {
		super();
	}

	public Topping(Integer id, String name, Integer priceM, Integer priceL) {
		super();
		this.id = id;
		this.name = name;
		this.priceM = priceM;
		this.priceL = priceL;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Topping [id=" + id + ", name=" + name + ", priceM=" + priceM + ", priceL=" + priceL + ", deleted="
				+ deleted + "]";
	}

}
