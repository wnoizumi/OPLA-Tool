/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.dinf.gres.domain.oldgui.indicators;

import br.ufpr.dinf.gres.domain.oldgui.configuration.UserHome;
import br.ufpr.dinf.gres.domain.oldgui.utils.Utils;
import br.ufpr.dinf.gres.core.jmetal4.results.Execution;
import br.ufpr.dinf.gres.core.jmetal4.results.FunResults;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author elf
 * <p>
 * NAO Usada Mais. Deletar
 */
public class HypervolumeCreateDataFiles {

    /**
     * Dado um conjunto de ids (experiments). Deve gerar os arquivos
     * correspondentes para o hypervolume
     * <p>
     * TODO Validação: Os experimentos passados como argumento devem ter o mesmo número de rodadas e objetivos. ??
     *
     * @throws Exception
     */
    public Map<String, List<Double>> generateHyperVolumeFiles(String... ids) throws Exception {

        //Usado temporariamente. Após cálculos estes arquivos serão apagados.
        String pathToSaveFiles = UserHome.getOplaUserHome();

        Map<String, List<Double>> fileToContent = new HashMap<>();

        for (String id : ids) {
            String nameFile = (pathToSaveFiles + Utils.generateFileName(id)).replaceAll("\\s+", "");

            try (PrintWriter pw = new PrintWriter(new FileWriter(nameFile))) {
                List<Double> values = new ArrayList<>();
                for (Execution execution : br.ufpr.dinf.gres.domain.db.Database.getAllExecutionsByExperimentId(id)) {

                    for (FunResults fun : execution.getFuns()) {
                        String o = fun.getObjectives().trim().replace("|", " ");
                        String[] ov = o.split(" ");

                        for (int i = 0; i < ov.length; i++)
                            values.add(Double.parseDouble(ov[i]));

                        pw.write(o);
                        pw.write("\n");
                    }

                    pw.write("\n");
                    pw.write("\n");
                }
                fileToContent.put(nameFile, values);
            }
        }

        return fileToContent;

    }
}