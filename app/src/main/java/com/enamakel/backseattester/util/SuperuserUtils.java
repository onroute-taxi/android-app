package com.enamakel.backseattester.util;


import java.io.DataOutputStream;
import java.io.IOException;


public class SuperuserUtils {
    /**
     * Execute the given command with super-user permissions
     *
     * @param command The command to execute
     */
    public static void execute(String command) {
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(command + "\n");
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Execute the given commands with super-user permissions. Each commands gets executed one after
     * the other.
     *
     * @param commands The commands to executes
     */
    public static void execute(String[] commands) {
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (int i = 0; i < commands.length; i++) {
                outputStream.writeBytes(commands[i] + "\n");
                outputStream.flush();
            }

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}