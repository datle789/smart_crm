package crm.demo.Dto;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import crm.demo.models.Notification;
import crm.demo.models.User;
import crm.demo.utils.ErrorUtil;

public class CrmDto {
    private long id;

    private String customerName;

    private static final String PHONE_NUMBER_PATTERN = "^(\\+[0-9]{1,4})?([-\\s./0-9])?([0-9]+)$";

    ErrorUtil errorUtil = new ErrorUtil();

    private long phoneNumber;

    private String title;

    private String description;

    private String crmFile;

    private Date startDate;

    private Date endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private User user;

    private long userId;

    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        // Kiểm tra định dạng số điện thoại trước khi gán giá trị
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    // Kiểm tra định dạng số điện thoại bằng regex
    private boolean isValidPhoneNumber(long phoneNumber) {
        String phoneNumberStr = String.valueOf(phoneNumber);
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumberStr);
        return matcher.matches();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCrmFile() {
        return crmFile;
    }

    public void setCrmFile(String crmFile) {
        this.crmFile = crmFile;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
