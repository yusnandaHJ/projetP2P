package gui;

import representation.File;
import representation.Peer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
        refreshPeerList();
        refreshParametersLabels();
        runButtonListeners();
    }

    private void initMainFrame() {
        connectionPort = DEFAULT_PORT;
        selectedFolderPath = DEFAULT_FOLDER_PATH;
        peerList = new ArrayList<>(); // TODO: should read the file with the list of known peers
        peerList.add(new Peer("1","1234"));
        peerList.add(new Peer("2","12345"));
        peerList.add(new Peer("3","123456"));
        fileList = new ArrayList<>(); // TODO: should read the file list of the chosen folder
        mainframe = new MainFrame();
        mainframe.getDefaultPortParam().setText("Default : " + Integer.toString(DEFAULT_PORT)); // default port, can't change during execution
    }

    public void refreshParametersLabels() {
        mainframe.getSelectedFolder().setText(selectedFolderPath);
        mainframe.getPortParamField().setText(Integer.toString(connectionPort));
    }

    public void refreshFileList() {
        DefaultListModel<File> model = new DefaultListModel<>();
        mainframe.getFileList().setModel(model);

        for (File f : fileList) {
            model.addElement(f);
        }
    }

    public void refreshPeerList() {
        if(mainframe.getDeletePeerButton().isEnabled()) {
            mainframe.getDeletePeerButton().setEnabled(false);
        }
        DefaultListModel<Peer> model = new DefaultListModel<>();
        mainframe.getPeerList().setModel(model);

        for (Peer p : peerList) {
            model.addElement(p);
        }
    }

    public void runButtonListeners() {
        /* Listener for file list refresh button */
        mainframe.getRefreshFileListButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshFileList();
            }
        });

        /* Listener for folder button selection */
        mainframe.getSelectFolderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainframe.openFileChooser();
                if(mainframe.getSelectedFolder().getText() != null) {
                    selectedFolderPath = mainframe.getSelectedFolder().getText();
                }
                refreshParametersLabels();
                displayParamsConsole();
            }
        });

        /* Listener for peer list selection */
        mainframe.getPeerList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getPeerList().getSelectedValue() != null) {
                    mainframe.getDeletePeerButton().setEnabled(true);
                }
            }
        });

        /* Listener for peer add button */
        mainframe.getAddPeerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String peerUrl = mainframe.getPeerParamField().getText();
                if(!mainframe.getPeerParamField().getText().isEmpty()) {
                    peerList.add(new Peer("1",peerUrl));
                    mainframe.getPeerParamField().setText("");
                }
                refreshPeerList();
                displayParamsConsole();
            }
        });

        /* Listener for peer delete button */
        mainframe.getDeletePeerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JList pl = mainframe.getPeerList();
                peerList.remove(pl.getSelectedValue());
                ((DefaultListModel) pl.getModel()).remove(pl.getSelectedIndex());
                refreshPeerList();
                displayParamsConsole();
            }
        });

        /* Listener for save parameters button */
        mainframe.getSavePortParamButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainframe.getPortParamField().getText().isEmpty()) {
                    try {
                        connectionPort = Integer.parseInt(mainframe.getPortParamField().getText());
                    } catch (NumberFormatException ex) {
                        connectionPort = DEFAULT_PORT;
                    }
                }

                refreshParametersLabels();
                refreshPeerList();
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

    public List<Peer> getPeerList() {
        return peerList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
