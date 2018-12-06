package gui;

import representation.File;
import representation.Peer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class MainFrameController {
    public static final int DEFAULT_PORT = 0;
    public static final String DEFAULT_FOLDER_PATH = "/shared_folder";

    private int connectionPort;
    private String selectedFolderPath = DEFAULT_FOLDER_PATH;
    private List<Peer> peerList;
    private List<File> fileList;
    private MainFrame mainframe;

    public MainFrameController() {
        initMainFrame();
        refreshFileList();
        refreshParametersLabels();
        runButtonListeners();
    }

    private void initMainFrame() {
        connectionPort = DEFAULT_PORT;
        selectedFolderPath = DEFAULT_FOLDER_PATH;
        peerList = new ArrayList<>(); // TODO: should read the file with the list of known peers
        fileList = new ArrayList<>(); // TODO: should read the file list of the chosen folder
        mainframe = new MainFrame();
        mainframe.getDefaultPortParam().setText("Default : " + Integer.toString(DEFAULT_PORT)); // default port, can't change during execution
    }

    public void refreshParametersLabels() {
        mainframe.getSelectedFolder().setText(selectedFolderPath);
        mainframe.getPortParamField().setText(Integer.toString(connectionPort));
        //TODO : Jlist of peer -> mainframe.getPeerParam().setText();
    }

    public void refreshFileList() {
        DefaultListModel<File> model = new DefaultListModel<>();
        mainframe.getFileList().setModel(model);

        for (File f : fileList) {
            model.addElement(f);
        }
        /* TODO: connection with model to get new file list */
    }

    public void runButtonListeners() {
        /* Listener for file list refresh button */
        mainframe.getRefreshFileListButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshFileList();
            }
        });

        /* Listener for folder selection */
        mainframe.getSelectFolderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainframe.openFileChooser();
            }
        });

        /* Listener for save parameters button */
        mainframe.getSaveParamsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainframe.getPortParamField().getText().isEmpty()) {
                    try {
                        connectionPort = Integer.parseInt(mainframe.getPortParamField().getText());
                    } catch (NumberFormatException ex) {
                        connectionPort = DEFAULT_PORT;
                    }
                }

                if(selectedFolderPath != null) {
                    selectedFolderPath = mainframe.getSelectedFolder().getText();
                }

                if(!mainframe.getPeerParamField().getText().isEmpty()) {
                    peerList.add(new Peer("0",mainframe.getPeerParamField().getText()));
                    mainframe.getPeerParamField().setText("");
                }

                refreshParametersLabels();
                displayParamsConsole();
            }
        });
    }

    public void displayParamsConsole() {
        System.out.println("--- Parameters changes save ---");
        System.out.println("Connection port : " + connectionPort);
        System.out.println("Selected folder : " + selectedFolderPath);
        System.out.println("List of known peers : " + peerList.toString());
    }

    public void setPeerList(List<Peer> peerList) {
        this.peerList = peerList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
