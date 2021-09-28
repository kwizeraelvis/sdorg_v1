package model;

import common.FileUpload;
import dao.AccusationDao;
import dao.DeviceDao;
import dao.DeviceImageDao;
import dao.MovementDao;
import domain.Accusation;
import domain.Device;
import domain.DeviceImage;
import domain.EMovementStatus;
import domain.Movement;
import domain.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean
@SessionScoped
public class StudentModel {

    private User loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    private List<DeviceImage> myDevices = new DeviceImageDao().findByPerson(loggedInUser.getStudent());
    private Device device = new Device();
    private List<String> chosenImage = new ArrayList<>();
    private List<Accusation> accusations = new AccusationDao().findByStudent(loggedInUser.getStudent());
    private Accusation accusation = new Accusation();
    private Movement movement = new Movement();
    private List<Movement> movements = new MovementDao().findByStudent(loggedInUser.getStudent());
    private String movementId = new String();
    private Movement chosenMovement = new Movement();
    private String newDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());

    public void registerAccusation() {
        try {
            if (new AccusationDao().findByDeviceAndStatus(chosenMovement.getDevice(), "Raised") != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Device Already Reported"));
            } else {
                accusation.setStatus("Raised");
                accusation.setMovement(chosenMovement);
                accusation.setReportingPeriod(new Date());
                new AccusationDao().register(accusation);
                accusations = new AccusationDao().findByStudent(loggedInUser.getStudent());
                accusation = new Accusation();

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Complaint Raised"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resolveAccusation(Accusation accusation) {
        try {
            accusation.setStatus("Resolved");
            accusation.setResolvedPeriod(new Date());
            new AccusationDao().update(accusation);
            accusations = new AccusationDao().findByStudent(loggedInUser.getStudent());

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Complaint Resolved"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void chooseMovement(Movement movement) {
        chosenMovement = movement;
    }

    public String navigateEdit(DeviceImage d) {
        device = d.getDevice();
        return "adddevice.xhtml?faces-redirect=true";
    }

    public void registerStudentDevice() {
        if (new DeviceDao().findOne(Device.class, device.getDeviceId()) != null) {
            try {
                device.setUpdatedBy(loggedInUser.getStudent());
                device.setDateUpdated(new Date());
                new DeviceDao().update(device);

                myDevices = new DeviceImageDao().findByPerson(loggedInUser.getStudent());

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Device Updated"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (chosenImage.isEmpty()) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Upload Profile Image"));
                } else {
                    device.setMovementStatus(EMovementStatus.CHECKED_OUT);
                    device.setPerson(loggedInUser.getStudent());
                    device.setCreatedBy(loggedInUser.getStudent());
                    device.setDateCreated(new Date());
                    device.setUpdatedBy(loggedInUser.getStudent());
                    device.setDateUpdated(new Date());
                    new DeviceDao().register(device);

                    DeviceImage deviceImage = new DeviceImage();
                    for (String x : chosenImage) {
                        deviceImage.setPath(x);
                        deviceImage.setDevice(device);
                        new DeviceImageDao().register(deviceImage);
                    }
                    chosenImage.clear();
                    myDevices = new DeviceImageDao().findByPerson(loggedInUser.getStudent());

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Device Registered"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Upload(FileUploadEvent event) {
        chosenImage.add(new FileUpload().Upload(event, "C:\\Users\\dsmst\\Documents\\Last 4\\SDORG_App-master\\web\\uploads\\device\\"));
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<String> getChosenImage() {
        return chosenImage;
    }

    public void setChosenImage(List<String> chosenImage) {
        this.chosenImage = chosenImage;
    }

    public List<DeviceImage> getMyDevices() {
        return myDevices;
    }

    public void setMyDevices(List<DeviceImage> myDevices) {
        this.myDevices = myDevices;
    }

    public List<Accusation> getAccusations() {
        return accusations;
    }

    public void setAccusations(List<Accusation> accusations) {
        this.accusations = accusations;
    }

    public Accusation getAccusation() {
        return accusation;
    }

    public void setAccusation(Accusation accusation) {
        this.accusation = accusation;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public String getMovementId() {
        return movementId;
    }

    public void setMovementId(String movementId) {
        this.movementId = movementId;
    }

    public Movement getChosenMovement() {
        return chosenMovement;
    }

    public void setChosenMovement(Movement chosenMovement) {
        this.chosenMovement = chosenMovement;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

}
