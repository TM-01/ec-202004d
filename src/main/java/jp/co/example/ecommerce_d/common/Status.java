package jp.co.example.ecommerce_d.common;

/**
 * オーダーの工程に関する列挙型です
 * 
 * @author kazuteru.takahashi
 *
 */
public enum Status {
    BEFOREORDER(0, "注文前", 1),
    NOTPAYMENT(1, "未入金", 2),
    DEPOSITED(2, "入金済", 3),
    SHIPPED(3, "発送済", 0),
    DELIVERED(9, "キャンセル", 9),
    INVALID(-1,"無効",-1);
    
    private final int statusId;
    private final String statusName;
    private final int nextStatus;
    
    private Status(int statusId, String statusName, int nextStatus) {
        this.statusId = statusId;
        this.statusName = statusName;
        this.nextStatus = nextStatus;
    }
    
    public int getStatusId() {
        return statusId;
    }
    
    public String getStatusName() {
        return statusName;
    }
    
    public int getNextStatus() {
        return nextStatus;
    }
    
    public static Status getStatus(int statusId) {
        for (Status status : Status.values()) {
            if (status.statusId == statusId) {
                return status;
            }
        }
        throw new IllegalArgumentException("不正なステータスです");
    }
}
