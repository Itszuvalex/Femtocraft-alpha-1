package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.mco.Backup;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
class GuiScreenBackupRestoreTask extends TaskLongRunning
{
    private final Backup field_111254_c;

    final GuiScreenBackup field_111255_a;

    private GuiScreenBackupRestoreTask(GuiScreenBackup par1GuiScreenBackup, Backup par2Backup)
    {
        this.field_111255_a = par1GuiScreenBackup;
        this.field_111254_c = par2Backup;
    }

    public void run()
    {
        this.func_96576_b(I18n.func_135053_a("mco.backup.restoring"));

        try
        {
            McoClient mcoclient = new McoClient(this.func_96578_b().func_110432_I());
            mcoclient.func_111235_c(GuiScreenBackup.func_110367_b(this.field_111255_a), this.field_111254_c.field_110727_a);

            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedexception)
            {
                Thread.currentThread().interrupt();
            }

            this.func_96578_b().displayGuiScreen(GuiScreenBackup.func_130031_d(this.field_111255_a));
        }
        catch (ExceptionMcoService exceptionmcoservice)
        {
            GuiScreenBackup.func_130035_e(this.field_111255_a).getLogAgent().logSevere(exceptionmcoservice.toString());
            this.func_96575_a(exceptionmcoservice.toString());
        }
        catch (Exception exception)
        {
            this.func_96575_a(exception.getLocalizedMessage());
        }
    }

    GuiScreenBackupRestoreTask(GuiScreenBackup par1GuiScreenBackup, Backup par2Backup, GuiScreenBackupDownloadThread par3GuiScreenBackupDownloadThread)
    {
        this(par1GuiScreenBackup, par2Backup);
    }
}
