package recognition.network;

public class Network {
    private Layer[] layers;

    public void initLayers(int[] layerSizes) {
        layers = new Layer[layerSizes.length];
        createLayers(layerSizes);
    }

    public void createLayers(int[] layerSizes) {
        for (int i = 0; i < layerSizes.length; i++) {
            int previousLayerSize = i != 0 ? layerSizes[i - 1] : 0;
            layers[i] = new Layer(layerSizes[i], previousLayerSize);
            if (i > 0) {
                // add bias to previous layer with connections to current
                layers[i - 1].addBias(layerSizes[i]);
            }
        }
        layers[0].setType(Layer.Type.INPUT);
        layers[layerSizes.length - 1].setType(Layer.Type.OUTPUT);
    }

    public Layer[] getLayers() {
        return layers;
    }

    public Layer getLayer(int index) {
        return layers[index];
    }

    public void setLayers(Layer[] layers) {
        this.layers = layers;
    }

    public int layerCount() {
        return layers.length;
    }

    public Layer getOutputLayer() {
        return layers[layerCount() - 1];
    }
}
